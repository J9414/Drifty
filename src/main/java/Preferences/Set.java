package Preferences;

import Enums.Program;
import GUI.Support.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.hildan.fxgson.FxGson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import static Preferences.Labels.*;

public class Set { // This class is used to set the user preferences
    private static final Set INSTANCE = new Set();
    private final Preferences preferences = Labels.PREFERENCES;

    private Set() {}

    protected static Set getInstance() {
        return INSTANCE;
    }

    public void folders(Folders folders) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Folders.class, new FoldersTypeAdapter());
        Gson gson = FxGson.addFxSupport(gsonBuilder).setPrettyPrinting().create();
        String value = gson.toJson(folders);
        AppSettings.clear.folders();
        preferences.put(FOLDERS.toString(), value);
    }

    public void mainAutoPaste(boolean isMainAutoPasteEnabled) {
        AppSettings.clear.mainAutoPaste();
        preferences.putBoolean(MAIN_AUTO_PASTE.toString(), isMainAutoPasteEnabled);
    }

    public void batchAutoPaste(boolean isBatchAutoPasteEnabled) {
        AppSettings.clear.batchAutoPaste();
        preferences.putBoolean(BATCH_AUTO_PASTE.toString(), isBatchAutoPasteEnabled);
    }

    public void lastYt_DlpUpdateTime(long lastYt_DlpUpdateTime) {
        AppSettings.clear.lastDLPUpdateTime();
        preferences.putLong(LAST_YT_DLP_UPDATE_TIME.toString(), lastYt_DlpUpdateTime);
    }

    public void lastFolder(String lastFolderPath) {
        AppSettings.clear.lastFolder();
        preferences.put(LAST_FOLDER.toString(), lastFolderPath);
    }

    public void batchDownloadJobs(Jobs jobs) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Jobs.class, new JobsTypeAdapter());
        gsonBuilder.registerTypeAdapter(Job.class, new JobTypeAdapter());
        Gson gson = FxGson.addFxSupport(gsonBuilder).setPrettyPrinting().create();
        String value = gson.toJson(jobs);
        AppSettings.clear.jobs();
        Path batchJobsFile = Paths.get(Program.get(Program.DATA_PATH), JOBS.toString());
        try {
            FileUtils.writeStringToFile(batchJobsFile.toFile(), value, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void startMax(boolean value) {
        AppSettings.clear.startMax();
        preferences.putBoolean(START_MAX.toString(), value);
    }

    public void startTime() {
        AppSettings.clear.startTime();
        preferences.putLong(START_TIME.toString(), System.currentTimeMillis());
    }

    public void jobHistory(JobHistory jobHistory) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Jobs.class, new JobsTypeAdapter());
        gsonBuilder.registerTypeAdapter(Job.class, new JobTypeAdapter());
        gsonBuilder.registerTypeAdapter(JobHistory.class, new JobHistoryTypeAdapter());
        Gson gson = FxGson.addFxSupport(gsonBuilder).setPrettyPrinting().create();
        String value = gson.toJson(jobHistory);
        AppSettings.clear.jobHistory();
        Path jobHistoryFile = Paths.get(Program.get(Program.DATA_PATH), JOB_HISTORY.toString());
        try {
            FileUtils.writeStringToFile(jobHistoryFile.toFile(), value, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
