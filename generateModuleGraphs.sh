#!/bin/bash
#
#
#   Copyright 2023 Einstein Blanco
#
#   Licensed under the GNU General Public License v3.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       https://www.gnu.org/licenses/gpl-3.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#
#

# Check if the dot command is available
if ! command -v dot &> /dev/null
then
    echo "The 'dot' command is not found. This is required to generate SVGs from the Graphviz files."
    echo "Installation instructions:"
    echo "  - On macOS: You can install Graphviz using Homebrew with the command: 'brew install graphviz'"
    echo "  - On Ubuntu: You can install Graphviz using APT with the command: 'sudo apt-get install graphviz'"
    echo "  - Others: Visit https://graphviz.org/download/"
    exit 1
fi

# Initialize an array to store excluded modules
excluded_modules=()

# Parse command-line arguments for excluded modules
while [[ $# -gt 0 ]]; do
    case "$1" in
        --exclude-module)
            excluded_modules+=("$2")
            shift # Past argument
            shift # Past value
            ;;
        *)
            echo "Unknown parameter passed: $1"
            exit 1
            ;;
    esac
done

# Get the module paths
module_paths=$(./gradlew -q printModulePaths --no-configuration-cache)

# Ensure the output directory exists
mkdir -p docs/images/graphs/

# Function to check and create a README.md for modules which don't have one.
check_and_create_readme() {
    local module_path="$1"
    local file_name="$2"

    # Remove the leading colon and replace colons with slashes to create a directory path
    local dir_path="${module_path:1}" # Remove leading colon
    dir_path=${dir_path//:/\/}        # Replace colons with slashes

    # Ensure we are using the correct path format for Windows (convert slashes to backslashes)
    if is_windows; then
        dir_path=$(echo "$dir_path" | sed 's/\//\\/g')  # Convert forward slashes to backslashes for Windows
    fi

    # Define the path to the README.md file
    local readme_file="${dir_path}\\README.md"

    # Create the README.md file if it doesn't exist
    if [[ ! -f "$readme_file" ]]; then
        echo "Creating README.md for ${module_path}"

        # Determine the relative path for the image based on the module's depth
        local depth
        depth=$(awk -F: '{print NF-1}' <<< "${module_path}")

        local relative_image_path="../"
        for ((i = 1; i < depth; i++)); do
            relative_image_path+="../"
        done
        relative_image_path+="docs/images/graphs/${file_name}.svg"

        # Write the README.md content
        echo "# ${module_path} module" > "$readme_file"
        echo "## Dependency graph" >> "$readme_file"
        echo "![Dependency graph](${relative_image_path})" >> "$readme_file"
    fi
}

# Function to check if the system is Windows
is_windows() {
    if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
        return 0  # Return true (0) if Windows
    else
        return 1  # Return false (1) otherwise
    fi
}

# Loop through each module path
echo "$module_paths" | while read -r module_path; do
    if is_windows; then
      module_path=$(echo "$module_path" | tr -d '\r')
    fi

    if [[ ! " ${excluded_modules[*]} " =~  ${module_path}  ]]; then
        file_name="dep_graph${module_path//:/_}"
        file_name="${file_name//-/_}"

        check_and_create_readme "$module_path" "$file_name"

        # Use a cross-platform temp directory
        temp_dir="${TEMP:-/tmp}"
        temp_file=$(mktemp "$temp_dir/${file_name}.XXXXXX.gv")

        # Generate .gv file
        ./gradlew generateModulesGraphvizText \
            -Pmodules.graph.output.gv="$temp_file" \
            -Pmodules.graph.of.module="${module_path}" </dev/null

        # Convert .gv to .svg
        dot -Tsvg "$temp_file" > "docs/images/graphs/${file_name}.svg"

        # Remove temp file
        rm -f "$temp_file"
    fi
done
