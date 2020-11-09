package tk.gushizone.excel.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SimpleAnalysisEventListener<T> extends AnalysisEventListener<T> {

    private List<T> results;

    public SimpleAnalysisEventListener() {
        results = Lists.newArrayListWithExpectedSize(2000);
    }

    public List<T> fetchResults() {
        return this.results;
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        results.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        log.info("数据解析完成，共计{}条。", results.size());
    }
}