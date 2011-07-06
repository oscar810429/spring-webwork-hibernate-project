package com.painiu.webapp.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExcelUtils {

	protected Log log = LogFactory.getLog(this.getClass());
	
	HttpServletRequest request ;
	HttpServletResponse response ;
	
	/**
	 * 需要流的支持
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */
	public ExcelUtils (HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request ;
		this.response = response ;
	}
	
	public static ExcelFont buildExcelFont(){
		return new ExcelUtils.ExcelFont();
	}
	
	/**
	 * EXCEL导出功能，导出格式如下：<br>
	 * <code>
	 * |---------------------|<br>
	 * |  ID |  名称  |  价格 |<br>
	 * |---------------------|<br>
	 * |  1  |  手机  |1800.0 |<br>
	 * |---------------------|<br>
	 * List<ExcelFont> titles 存放着导出格式的Excel标题格式<br>
	 * {
	 * titles.add(new ExcelFont("ID", "RED"));
	 * titles.add(new ExcelFont("名称", "BLUE"));
	 * titles.add(new ExcelFont("价格"));
	 * }
	 * <br>
	 * Map<Object, List<Object[]>> contents存放着导出格式的Excel内容。<br>
	 * {
	 * objs为对应单元格的内容
	 * Object[] objs = new Object[titles.size()];
	 * objs[0] = "1";
	 * objs[1] = "手机";
	 * objs[2] = "1800.0";
	 *
	 * 把存放单元格内容的objs放到一个List里进行
	 * List<Object[]> list = new ArrayList<Object[]>();
	 * list.add(objs);
	 * 
	 * 列表ID和List
	 * contents.put(objs[1], list);
	 * 
	 * }
	 * </code>
	 * 
	 * @param fileName 导出文件名
	 * @param titles 导出EXCEL的title
	 * @param contents 导出EXCEL的内宅
	 * @return 导出是否成功
	 * @author HuangFeng-(Nov 26, 2009)
	 */
	public int out(String fileName, List<ExcelFont> titles, Map<Object, List<Object[]>> contents) {

		try {
			OutputStream os =  response.getOutputStream();
			//设定输出文件头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "iso8859-1") + ".xls");
			// 定义输出类型
			response.setContentType("application/msexcel");
			// 建立excel文件输出流
			WritableWorkbook wbook = Workbook.createWorkbook(os);
			
			//String title = fileName;
			// 创建excel页码
			WritableSheet wsheet = wbook.createSheet("firest page", 0);
			
			// 设置excel标题字体   16号 加粗，黑色
			//WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);    
			//WritableCellFormat wcformat = new WritableCellFormat(wfont);

			// 添加标题
			//wsheet.addCell(new Label(1, 0, title, wcformat));
			
			//wsheet.addCell(new Label(0, 1, "导出时间：" + DateUtil.format(new Date())));
			//wsheet.addCell(new Label(1, 1, "导出人：" + "JWinder"));
			
			//标题从第一行开始
			//添加标题
			for (int i = 0; i < titles.size(); i++)
			{
				ExcelFont titleFont = titles.get(i);
				WritableCellFormat wcformat = getWritableCellFormat(titleFont);
				wsheet.setColumnView(i, 30);
				wsheet.addCell(new Label(i, 0, titleFont.fontContent, wcformat));
			}
			
			//添加内容
			//内容默认从第二行开始
			int y = 1;
			//遍历MAP的KEY
			for (Iterator<?> itor = contents.keySet().iterator(); itor.hasNext();)
			{
				//根据KEY获得List列表，进行列表循环输出
				List<Object[]> list = (List<Object[]>) contents.get(itor.next());
				for (Object[] obj : list)
				{
					//根据Titles长度进行遍历内容
					for (int i = 0; i < titles.size(); i++)
					{
						wsheet.addCell(new Label(i, y, (obj[i] == null)? "" : obj[i].toString()));
					}
					y++ ;
				}
			}

			// 写入文件
			wbook.write();
			wbook.close();
			os.flush();
			os.close();
			
		} catch (IOException e) {
			log.error("Excel File Error", e);
			return 0;
		} catch (RowsExceededException e) {
			log.error("Excel Cell Error", e);
			return 0;
		} catch (WriteException e) {
			log.error("AddExcel Cell Error", e);
			return 0;
		}
		
		return 1;
	}
	
	private WritableCellFormat getWritableCellFormat(ExcelFont excelFont)
	{
		int size = excelFont.size;
		//BoldStyle bold = WritableFont.BOLD;
		Colour color = getColor(excelFont.color);
		// 设置以内容标题的字体
		WritableFont wfont = null;
		
		if(excelFont.bold)
		{
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, size, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, color);    
		} else {
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, size, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, color); 
		}
		
		WritableCellFormat wcformat = new WritableCellFormat(wfont);
		return wcformat;
	}
	
	/**
	 * 字体颜色
	 * @param color 颜色大写字母 
	 * @return Colour
	 * @author HuangFeng-(Nov 26, 2009)
	 */
	private Colour getColor(String color)
	{
		//需要其他颜色请加入
		if(color.equals("BLACK"))
		{
			return Colour.BLACK;
		}
		if(color.equals("RED"))
		{
			return Colour.RED;
		}
		if(color.equals("BLUE"))
		{
			return Colour.BLUE;
		}
		
		return Colour.BLACK;
	}
	
	/**
	 * 导入Excel内容，只支持第一个Sheet工作簿
	 * @param fileName excel文件绝对路径名称，如：<code>E:\excel\1.xls</code>
	 * @return <code>List<Object[]></code> Excel单元格内容数组列表
	 * @author HuangFeng-(Nov 26, 2009)
	 */
	public List<Object[]> read(String fileName)
	{
		//1、选取Excel文件，
		//2、选择工作簿，
		//3、选择Cell，
		//4、读取信息。
		Workbook book = null ;
		try {
			//通过Workbook的静态方法getWorkbook选取Excel文件
			book = Workbook.getWorkbook(new File(fileName));
			
			//通过Workbook的getSheet方法选择第一个工作簿（从0开始）
			Sheet sheet = book.getSheet(0);
			
			//得到当前工作表的行数,和列长  
			int rows = sheet.getRows();
	       	int cols = sheet.getColumns();
	       	
	       	return getExcelToCache(sheet, cols, 0, rows);
	       	
		}
		catch (IOException e)
		{
			log.error("Read Excel File Error", e);
			return null;
		}
		catch (BiffException e)
		{
			log.error("Read Excel File Error", e);
			return null;
		}
		finally
		{
			if (null != book)
			{
				book.close();
			}
		}
	}
	
	/**
	 * 将数据读取到缓存中
	 * @param sheet 工作簿
	 * @param colsLength 标题列长或是内容列表列长
	 * @param statIndex 开始行标，读取开始行
	 * @param endIndex 结束行标，读取结束行
	 * @return <code>List<Object[]></code> Excel单元格内容数组列表
	 * @author HuangFeng-(Nov 26, 2009)
	 */
	private List<Object[]> getExcelToCache(Sheet sheet, int colsLength, int statIndex, int endIndex)
	{
		List<Object[]> list = new ArrayList<Object[]>();
		
		//根据开始行标-结束行标读取行记录
		for (int i = statIndex; i < endIndex; i++)
        {
			//根据行和列长读取单元格
			Object[] objs = new Object[colsLength];
			for (int c = 0; c < colsLength; c++)
			{
				objs[c] = getCellContents(sheet, c, i);
			}
			list.add(objs);
		}
		
		return list;
	}
	
	/**
	 * Method：读取EXCEL单元格<br>
	 * Remark：以二维数组形式，上下标确定单元格内容<br>
	 * Author：HF-JWinder(2009-3-5 上午09:46:57)
	 * @param sheet Sheet 工作簿
	 * @param x 二组数组上标
	 * @param y 二组数组下标
	 * @return String 单元格式内容
	 */
	private String getCellContents(Sheet sheet, int x, int y)
	{
		Cell cell = sheet.getCell(x, y);
		return cell.getContents();
	}
	
	/**
	 * EXCEL单元格字体样式
	 * @author HuangFeng
	 * @version 1.0 (Nov 26, 2009)
	 * @copyright 杭州三六五电子商务股份有限公司
	 * @since EShop1.0
	 */
	public static class ExcelFont
	{
		public ExcelFont()
		{
		}
		
		public ExcelFont(String fontContent)
		{
			this.fontContent = fontContent;
		}
		
		public ExcelFont(String fontContent, String color)
		{
			this.fontContent = fontContent;
			this.color = color.toUpperCase();
		}
		
		public ExcelFont(String fontContent, int size)
		{
			this.fontContent = fontContent;
			this.size = size;
		}
		
		public ExcelFont(String fontContent, boolean bold)
		{
			this.fontContent = fontContent;
			this.bold = bold;
		}
		
		public ExcelFont(String fontContent, String color, int size,  boolean bold)
		{
			this.fontContent = fontContent;
			this.color = color ;
			this.size = size;
			this.bold = bold;
		}
		
		/** 单元格内容 */
		private String fontContent = "";
		
		/** 颜色 */
		private String color = "black";
		
		/** 大小 */
		private int size = 10;
		
		/** 是否加粗 */
		boolean bold = true;
		
	}
}
