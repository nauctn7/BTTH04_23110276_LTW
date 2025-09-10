package vn.iotstar.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class UploadUtils {

    public static String saveImage(HttpServletRequest req, Part part) throws Exception {
        if (part == null || part.getSize() == 0) return null;

        String uploadDir = req.getServletContext().getRealPath("/uploads");
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String submitted = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String ext = "";
        int dot = submitted.lastIndexOf('.');
        if (dot >= 0) ext = submitted.substring(dot).toLowerCase();

        String[] allowed = {".png", ".jpg", ".jpeg", ".gif", ".webp"};
        boolean ok = false;
        for (String a : allowed) if (ext.equals(a)) { ok = true; break; }
        if (!ok) throw new IllegalArgumentException("Định dạng ảnh không hợp lệ");

        String filename = UUID.randomUUID().toString().replace("-", "") + ext;
        File dest = new File(dir, filename);
        part.write(dest.getAbsolutePath());

        return filename; // lưu vào DB
    }

    public static void deleteImage(HttpServletRequest req, String filename) {
        if (filename == null || filename.isBlank()) return;
        String uploadDir = req.getServletContext().getRealPath("/uploads");
        File f = new File(uploadDir, filename);
        if (f.exists()) f.delete();
    }
}
