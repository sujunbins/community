package life.sujunbin.community.community.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/5 15:25
 */
@Data
public class Pagintion {
    private List<QuestionDTO> questionDTOS;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalpage;

    public void setPagintion(int totalCount, Integer page, Integer size) {

        if (totalCount % size == 0) {
            totalpage = totalCount / size;

        } else {
            totalpage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalpage) {
            page = totalpage;
        }
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalpage) {
                pages.add(page + i);
            }


        }
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        if (page == totalpage) {
            showNext = false;
        } else {
            showNext = true;

        }
        if (pages.contains(1)) {
            showFirstPage = false;

        } else {
            showFirstPage = true;
        }
        if (pages.contains(totalpage)) {
            showEndPage = false;

        } else {
            showEndPage = true;
        }

    }
}

