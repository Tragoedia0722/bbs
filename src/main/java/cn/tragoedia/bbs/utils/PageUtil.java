package cn.tragoedia.bbs.utils;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageUtil {
    private int currentPage = 1; // 当前页面
    private int size = 10; // 每页上限
    private int rows; // 数据总数
    private String path; // 查询路径

    public void setCurrentPage(int currentPage) {
        if (currentPage >= 1) {
            this.currentPage = currentPage;
        }
    }

    public void setSize(int size) {
        if (size >= 1 && size <= 100) {
            this.size = size;
        }
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTotalPage() {
        if (rows % size == 0) {
            return rows / size;
        } else {
            return rows / size + 1;
        }
    }

    public int getFromPage() {
        int form = currentPage - 2;
        return form < 1 ? 1 : form;
    }

    public int getToPage() {
        int to = currentPage + 2;
        int total = getTotalPage();
        return to > total ? total : to;
    }
}
