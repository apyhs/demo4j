package demo.demo.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class Dom4jDemo {

	@Test
	public void testXmlWrite() throws Exception {
		Document doc = DocumentHelper.createDocument();
		// 增加根节点
		Element books = doc.addElement("books");
		// 增加子元素
		Element book1 = books.addElement("book");
		Element title1 = book1.addElement("title");
		Element author1 = book1.addElement("author");

		Element book2 = books.addElement("book");
		Element title2 = book2.addElement("title");
		Element author2 = book2.addElement("author");

		// 为子节点添加属性
		book1.addAttribute("id", "001");
		// 为元素添加内容
		title1.setText("Harry Potter");
		author1.setText("J K. Rowling");

		book2.addAttribute("id", "002");
		title2.setText("Learning XML");
		author2.setText("Erik T. Ray");

		// 实例化输出格式对象
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置输出编码
		format.setEncoding("UTF-8");
		// 创建需要写入的File对象
		File file = new File("E:\\books.xml");
		// 生成XMLWriter对象，构造函数中的参数为需要输出的文件流和格式
		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		// 开始写入，write方法中包含上面创建的Document对象
		writer.write(doc);
	}

	@Test
	public void testXmlRead() throws Exception {
		SAXReader reader = new SAXReader();
		File file = new File("e:\\books.xml");
		Document document = reader.read(file);
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();
		for (Element child : childElements) {
			// 未知属性名情况下
			/*
			 * List<Attribute> attributeList = child.attributes(); for
			 * (Attribute attr : attributeList) {
			 * System.out.println(attr.getName() + ": " + attr.getValue()); }
			 */

			// 已知属性名情况下
			System.out.println("id: " + child.attributeValue("id"));

			// 未知子元素名情况下
			/*
			 * List<Element> elementList = child.elements(); for (Element ele :
			 * elementList) { System.out.println(ele.getName() + ": " +
			 * ele.getText()); } System.out.println();
			 */

			// 已知子元素名的情况下
			System.out.println("title" + child.elementText("title"));
			System.out.println("author" + child.elementText("author"));
			// 这行是为了格式化美观而存在
			System.out.println();
		}
	}

}
