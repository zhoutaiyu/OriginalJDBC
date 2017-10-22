package edu.cn;

import edu.cn.pojo.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周太宇 on 2017/10/22.
 */
public class OriginalJDBC {
    private final static String DRIVEFORNAME = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/book";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "1234";

    /**
     * 查找type="{}"的数据
     *
     * @return
     */
    public List<Book> findByType(String type) {
        //初始化
        Connection con = null;
        PreparedStatement pstmt = null;
        String sql = null;
        ResultSet rs = null;
        List<Book> bookList = new ArrayList<Book>();

        try {
            //装载驱动程序
            Class.forName(DRIVEFORNAME);
            //建立数据库连接
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //编写sql，参数用?代替
            sql = "select * from bookta where type=?";
            //获得语句对象，承载sql语句
            pstmt = con.prepareStatement(sql);
            //设置参数
            pstmt.setString(1, type);
            //执行sql语句
            rs = pstmt.executeQuery();
            //获取执行结果
            while (rs.next()) {
                Book book = new Book();
                //数据库记录->实体对象
                book.setIsbn(rs.getString(1));
                book.setTitle(rs.getString(2));
                book.setType(rs.getString(3));
                book.setPrice(rs.getDouble(4));
                //对象加入集合
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally代码块特点：抛出异常也会运行
            //负责关闭资源，先申请的资源的后关闭
            try {
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e1) {
                //ignore
            }
            return bookList;
        }
    }
    /**
     * 测试sql注入
     */
    public List<Book> findByTypeSQLInsert(String type) {
        //初始化
        Connection con = null;
        Statement statement = null;
        String sql = null;
        ResultSet rs = null;
        List<Book> bookList = new ArrayList<Book>();

        try {
            //装载驱动程序
            Class.forName(DRIVEFORNAME);
            //建立数据库连接
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //编写sql
            sql = "select * from bookta where type='"+type+"'";
            //获得语句对象
            statement = con.createStatement();
            //执行sql语句
            rs = statement.executeQuery(sql);
            //获取执行结果
            while (rs.next()) {
                Book book = new Book();
                //数据库记录->实体对象
                book.setIsbn(rs.getString(1));
                book.setTitle(rs.getString(2));
                book.setType(rs.getString(3));
                book.setPrice(rs.getDouble(4));
                //对象加入集合
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally代码块特点：抛出异常也会运行
            //负责关闭资源，先申请的资源的后关闭
            try {
                if (con != null) {
                    con.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e1) {
                //ignore
            }
            return bookList;
        }
    }
}
