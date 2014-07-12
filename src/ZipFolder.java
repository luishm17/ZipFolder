import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFolder {
	private static final String ZIP_FORMAT = ".zip";
	private static final String tmp = "Fairy Tail";
	private static final String SOURCE_FOLDER = "G:\\Manga\\" + tmp; // SourceFolder
																		// path
	private static final String OUTPUT_SOURCE_FOLDER = "G:\\Manga\\" + tmp;

	public ZipFolder() {
	}

	public static void main(String[] args) {
		ZipFolder appZip = new ZipFolder();
		String[] directories = appZip.getSubDirectory();

		List<String> fileList;
		String source;
		System.out.println("<-------------------------->");
		System.out.println("<-------------------------->");
		System.out.println("<-------------------------->");
		System.out.println("<-------------------------->");
		System.out.println(directories.length
				+ " Directories are going to be processed");
		System.out.println("<-------------------------->");
		System.out.println("<-------------------------->");
		System.out.println("<-------------------------->");
		System.out.println("<-------------------------->");

		File output = new File(OUTPUT_SOURCE_FOLDER);
		if (output.exists() ^ true) {
			System.out.println("The folder " + OUTPUT_SOURCE_FOLDER
					+ " was created");
			output.mkdir();
		}

		Thread thread = new Thread();

		try {
			thread.sleep(2000);
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String directory : directories) {
			source = SOURCE_FOLDER + File.separator + directory;
			System.out.println("The source is " + source);
			fileList = new ArrayList<String>();
			appZip.generateFileList(new File(source), fileList);
			System.out.println("Folder size is " + fileList.size());
			appZip.zipIt(OUTPUT_SOURCE_FOLDER + File.separator + directory
					+ ZIP_FORMAT, fileList, source);
		}

		System.out.println("El proceso a terminado");
	}

	public void zipIt(String zipFile, List<String> fileList, String source) {
		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {

			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);
			FileInputStream in = null;

			for (String file : fileList) {
				// System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(SOURCE_FOLDER + File.separator
							+ file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					in.close();
				}
			}

			zos.closeEntry();
			System.out.println("Folder successfully compressed");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> generateFileList(File node, List<String> fileList) {
		// System.out.println("The node is " + node.getName());

		// add file only
		if (node.isFile()) {
			// System.out.println("File Added");
			fileList.add(generateZipEntry(node.toString()));

		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename), fileList);
			}
		}

		return fileList;
	}

	private String generateZipEntry(String file) {
		return file.substring(SOURCE_FOLDER.length() + 1, file.length());
	}

	private String[] getSubDirectory() {

		File file = new File(SOURCE_FOLDER);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
		System.out.println(Arrays.toString(directories));

		return directories;
	}

}
