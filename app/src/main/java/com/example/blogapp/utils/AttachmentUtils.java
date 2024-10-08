package com.example.blogapp.utils;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.webkit.MimeTypeMap; // Ensure you have this import for MIME types
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;

public class AttachmentUtils {

    public static void openDocument(Context context, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            Toast.makeText(context, "No attachment", Toast.LENGTH_SHORT).show();
            return; // Early exit if filePath is invalid
        }

        File file = new File(filePath);
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // For Android Nougat and above, use FileProvider
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        } else {
            // For below Nougat, use Uri.fromFile
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, getMimeType(file.getAbsolutePath()));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission to the other app to read the file

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the error if no app can open the file
            Toast.makeText(context, "No application found to open this file", Toast.LENGTH_SHORT).show();
        }
    }

    public static void downloadAndOpenDocument(Context context, String url) {
        if (url == null || url.isEmpty()) {
            Toast.makeText(context, "No attachment", Toast.LENGTH_SHORT).show();
            return; // Early exit if URL is invalid
        }

        String fileName = "sample.pdf"; // Adjust as needed
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("Downloading File");
        request.setDescription("Downloading " + fileName);
        request.setDestinationUri(Uri.fromFile(file));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            long downloadId = downloadManager.enqueue(request);

            // Set up a listener for when the download is complete
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (cursor.getInt(columnIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                        openDocument(context, file.getAbsolutePath()); // Call the openDocument method here
                    } else {
                        Toast.makeText(context, "Failed to download file.", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }, 500); // Delay to give time for download to start
        } else {
            Toast.makeText(context, "Download manager is unavailable.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openVideo(Context context, String videoUrl) {
        if (videoUrl == null || videoUrl.isEmpty()) {
            Toast.makeText(context, "No video to open", Toast.LENGTH_SHORT).show();
            return; // Early exit if videoUrl is invalid
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        intent.setDataAndType(Uri.parse(videoUrl), "video/*");

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found to open this video", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openWebUrl(Context context, String url) {
        if (url == null || url.isEmpty()) {
            Toast.makeText(context, "No URL to open", Toast.LENGTH_SHORT).show();
            return; // Early exit if URL is invalid
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application found to open this URL", Toast.LENGTH_SHORT).show();
        }
    }

    private static String getMimeType(String filePath) {
        String type = "*/*"; // Default type
        String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(filePath)).toString());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
