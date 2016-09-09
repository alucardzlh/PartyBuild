package com.example.a25908.partybuild.Utils.net.download;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ?????????
 * 
 */
public class FileDownloader {
	private static final String TAG = "FileDownloader";
	private Context context;
	private FileService fileService;
	/* ?????? */
	private boolean exit;
	/* ????????????? */
	private int downloadSize = 0;
	/* ????????? */
	private int fileSize = 0;
	/* ????? */
	private DownloadThread[] threads;
	/* ?????????? */
	private File saveFile;
	/* ???????????????? */
	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
	/* ?????????????? */
	private int block;
	/* ????·?? */
	private String downloadUrl;

	/**
	 * ????????
	 */
	public int getThreadSize() {
		return threads.length;
	}

	/**
	 * ???????
	 */
	public void exit() {
		this.exit = true;
	}

	public boolean getExit() {
		return this.exit;
	}

	/**
	 * ????????С
	 * 
	 * @return
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * ??????????С
	 * 
	 * @param size
	 */
	protected synchronized void append(int size) {
		downloadSize += size;
	}

	/**
	 * ??????????????????λ??
	 * 
	 * @param threadId
	 *            ???id
	 * @param pos
	 *            ????????λ??
	 */
	protected synchronized void update(int threadId, int pos) {
		this.data.put(threadId, pos);
		this.fileService.update(this.downloadUrl, threadId, pos);
	}

	/**
	 * ?????????????
	 * 
	 * @param downloadUrl
	 *            ????·??
	 * @param fileSaveDir
	 *            ?????????
	 * @param threadNum
	 *            ?????????
	 */
	public FileDownloader(Context context, String downloadUrl,
						  File fileSaveDir, int threadNum) {
		try {
			this.context = context;
			this.downloadUrl = downloadUrl;
			fileService = new FileService(this.context);
			URL url = new URL(this.downloadUrl);
			if (!fileSaveDir.exists()) // ?ж??????????????????????????
				fileSaveDir.mkdirs();
			this.threads = new DownloadThread[threadNum];// ????????????
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty(
					"Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", downloadUrl);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect(); // ????
			printResponseHeader(conn);
			if (conn.getResponseCode() == 200) { // ??????
				this.fileSize = conn.getContentLength();// ???????????????С
				if (this.fileSize <= 0)
					throw new RuntimeException("Unkown file size ");

				String filename = getFileName(conn);
				String[] str=filename.split("\\.");
				try {
					// URL?????????????????????URL????
//					filename = URLEncoder.encode(str[0], "utf-8");// ??????????
					filename=Uri.decode(str[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}

				this.saveFile = new File(fileSaveDir, filename+"."+str[1]);// ???????????
				Map<Integer, Integer> logdata = fileService
						.getData(downloadUrl);// ?????????
				if (logdata.size() > 0) {// ?????????????
					for (Map.Entry<Integer, Integer> entry : logdata.entrySet())
						data.put(entry.getKey(), entry.getValue());// ??????????????????????????data??
				}
				if (this.data.size() == this.threads.length) {// ???????????????????????????????
					for (int i = 0; i < this.threads.length; i++) {
						this.downloadSize += this.data.get(i + 1);
					}
					print("???????????" + this.downloadSize);
				}
				// ??????????????????????
				this.block = (this.fileSize % this.threads.length) == 0 ? this.fileSize
						/ this.threads.length
						: this.fileSize / this.threads.length + 1;
			} else {
				throw new RuntimeException("server no response ");
			}
		} catch (Exception e) {
			print(e.toString());
			throw new RuntimeException("don't connection this url");
		}
	}

	/**
	 * ????????
	 */
	private String getFileName(HttpURLConnection conn) {
		String filename = this.downloadUrl.substring(this.downloadUrl
				.lastIndexOf('/') + 1);
		if (filename == null || "".equals(filename.trim())) {// ?????????????????
			for (int i = 0;; i++) {
				String mine = conn.getHeaderField(i);
				if (mine == null)
					break;
				if ("content-disposition".equals(conn.getHeaderFieldKey(i)
						.toLowerCase())) {
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(
							mine.toLowerCase());
					if (m.find())
						return m.group(1);
				}
			}
			filename = UUID.randomUUID() + ".tmp";// ????????????
		}
		return filename;
	}

	/**
	 * ??????????
	 * 
	 * @param listener
	 *            ??????????????仯,??????????????????????,?????????null
	 * @return ???????????С
	 * @throws Exception
	 */
	public int download(DownloadProgressListener listener) throws Exception {
		try {
			RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
			if (this.fileSize > 0)
				randOut.setLength(this.fileSize); // ?????fileSize??С
			randOut.close();
			URL url = new URL(this.downloadUrl);
			if (this.data.size() != this.threads.length) {// ??????δ???????????????????????????????????????
				this.data.clear();
				for (int i = 0; i < this.threads.length; i++) {
					this.data.put(i + 1, 0);// ???????????????????????????0
				}
				this.downloadSize = 0;
			}
			for (int i = 0; i < this.threads.length; i++) {// ??????????????
				int downLength = this.data.get(i + 1);
				if (downLength < this.block
						&& this.downloadSize < this.fileSize) {// ?ж?????????????????,???????????
					this.threads[i] = new DownloadThread(this, url,
							this.saveFile, this.block, this.data.get(i + 1),
							i + 1);
					this.threads[i].setPriority(7); // ????????????
					this.threads[i].start();
				} else {
					this.threads[i] = null;
				}
			}
			fileService.delete(this.downloadUrl);// ?????????????????????????????????
			fileService.save(this.downloadUrl, this.data);
			boolean notFinish = true;// ????δ???
			while (notFinish) {// ????ж??????????????????
				Thread.sleep(900);
				notFinish = false;// ????????????????
				for (int i = 0; i < this.threads.length; i++) {
					if (this.threads[i] != null && !this.threads[i].isFinish()) {// ??????????δ???????
						notFinish = true;// ?????????????????
						if (this.threads[i].getDownLength() == -1) {// ??????????,??????????
							this.threads[i] = new DownloadThread(this, url,
									this.saveFile, this.block,
									this.data.get(i + 1), i + 1);
							this.threads[i].setPriority(7);
							this.threads[i].start();
						}
					}
				}
				if (listener != null)
					listener.onDownloadSize(this.downloadSize);// ??????????????????????
			}
			if (downloadSize == this.fileSize)
				fileService.delete(this.downloadUrl);// ?????????????
		} catch (Exception e) {
			print(e.toString());
			throw new Exception("file download error");
		}
		return this.downloadSize;
	}

	/**
	 * ???Http???????
	 * 
	 * @param http
	 * @return
	 */
	public static Map<String, String> getHttpResponseHeader(
			HttpURLConnection http) {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
		}
		return header;
	}

	/**
	 * ???Http????
	 * 
	 * @param http
	 */
	public static void printResponseHeader(HttpURLConnection http) {
		Map<String, String> header = getHttpResponseHeader(http);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() + ":" : "";
			print(key + entry.getValue());
		}
	}

	private static void print(String msg) {
		Log.i(TAG, msg);
	}
}
