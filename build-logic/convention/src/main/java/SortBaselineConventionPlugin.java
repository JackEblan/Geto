import com.android.tools.profgen.ArtProfile;
import com.android.tools.profgen.ArtProfileKt;
import com.android.tools.profgen.ArtProfileSerializer;
import com.android.tools.profgen.DexFile;
import com.android.tools.profgen.DexFileData;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("KotlinInternalInJava")
public class SortBaselineConventionPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        target.afterEvaluate(p -> {
            for (Task task : p.getTasks()) {
                if (task.getName().startsWith("compile") && task.getName().endsWith("ReleaseArtProfile")) {
                    task.doLast(t -> {
                        for (File file : t.getOutputs().getFiles()) {
                            if (file.getName().endsWith(".profm")) {
                                System.out.println("Sorting " + file + " ...");
                                ArtProfileSerializer version = ArtProfileSerializer.valueOf("METADATA_0_0_2");
                                ArtProfile profile = ArtProfileKt.ArtProfile(file);
                                assert profile != null;
                                List<DexFile> keys = new ArrayList<>(profile.getProfileData().keySet());
                                Map<DexFile, DexFileData> sortedData = new LinkedHashMap<>();
                                keys.sort(DexFile.Companion);
                                keys.forEach(key -> sortedData.put(key, profile.getProfileData().get(key)));
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    fos.write(version.getMagicBytes$profgen());
                                    fos.write(version.getVersionBytes$profgen());
                                    version.write$profgen(fos, sortedData, "");
                                } catch (Exception e) {
                                    System.err.println("Error writing to file: " + e.getMessage());
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}