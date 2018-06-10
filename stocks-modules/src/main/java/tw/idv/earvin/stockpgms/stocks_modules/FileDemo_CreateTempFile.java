/**
 * File.createTempFile 的用途是你想要建立一個檔案暫時使用，但是你不在乎其精確的檔案名，
 * 隻要不覆蓋到已存在的檔案時。可以製定臨時檔案的檔案名稱首碼、尾碼及檔案所在的目錄，如果
 * 不指定目錄，則存放在系統的臨時資料夾下。
 * Ref Addr : http://www.xingzuomi.org/file+createtempfile/
 */
package tw.idv.earvin.stockpgms.stocks_modules;

import java.io.File;

public class FileDemo_CreateTempFile {
	public static void main(String[] args) {

		File f = null;

		try {
			// creates temporary file
			f = File.createTempFile("tmp", ".txt");

			// prints absolute path
			System.out.println("File path: " + f.getAbsolutePath());

			// creates temporary file
			f = File.createTempFile("tmp", null);

			// prints absolute path
			System.out.print("File path: " + f.getAbsolutePath());

		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
	}
}
