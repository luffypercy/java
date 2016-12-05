package cn.generatecode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class test {
	public static void main(String[] args)
			throws Exception {
		String system = System.getProperty("os.name");
		System.out.println(system);
	}
//
//	public InputStream generationPdfDzOrder(String htmlFileName,
//			String pdfFileName) throws Exception {
//		// final Template template =
//		// freemarkerconfiguration.getConfiguration().getTemplate(dzorderftl);
//		// String htmlText =
//		// FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
//		// String tmpFileName = UUID.randomUUID().toString(); //生成随机文件名
//		// File dir = new File(tmpdir);
//		// if(!dir.exists())
//		// dir.mkdirs();
//		// String htmlFileName = tmpdir + "/" + tmpFileName + ".html" ;
//		// File htmlFile = new File(htmlFileName); //html文件
//		// String pdfFileName = tmpdir + "/" + tmpFileName + ".pdf" ;
//		// IOUtils.write(htmlText, new FileOutputStream(htmlFile));
//		// //将内容写入html文件
//
//		String command = getCommand();
//		System.out.println(command);
////		File pdfFile = new File(pdfFileName); // pdf文件
////		if (!pdfFile.exists()) {
////			pdfFile.createNewFile();
////		}
//		Runtime.getRuntime().exec(command);
//		//Runtime.getRuntime().exec("nginx");
//		// TimeUnit.SECONDS.sleep(3);
//		return null;
//	}

	public static String getCommand() {
		String system = System.getProperty("os.name");
		System.out.println(system);
		 if("Windows XP".equalsIgnoreCase(system)) //xp系统
		return "D:/Program Files/wkhtmltopdf/wkhtmltopdf.exe";
		 else if("Linux".equalsIgnoreCase(system)) //linux 系统
			 return "wkhtmltopdf" ;
		 return "" ;
	}
}
