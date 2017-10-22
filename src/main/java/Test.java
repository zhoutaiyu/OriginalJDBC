import edu.cn.OriginalJDBC;
import edu.cn.pojo.Book;

import java.util.List;

/**
 * Created by 周太宇 on 2017/10/22.
 */
public class Test {
    public static void main(String[] args) {
        //测试
        OriginalJDBC originalJDBC=new OriginalJDBC();
        List<Book> bookList =originalJDBC.findByType("文学");
        //遍历
        for(Book book:bookList){
            System.out.println(book.getTitle()+"这本书价格为"+book.getPrice());
        }
        //测试sql注入
        System.out.println("测试sql注入");
        //测试sql注入，数据库中其实没有type="爱情"依然能查询数据库
        //select * from bookta where type='爱情' or '1=1'--'其他的限制条件';
        bookList=originalJDBC.findByTypeSQLInsert("爱情' or 1=1;-- ");
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println(bookList.get(i).getTitle() + "价格为" + bookList.get(i).getPrice());
        }
    }
}
