package reader.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reader.service.BookService;

import javax.annotation.Resource;

@Component
public class ComputeTask {
    @Resource
    private BookService bookService;
    @Scheduled(cron="0 * * * * ?")
    public void updateEvaluation() {
        bookService.updateEvaluation();
        System.out.println("update");
    }
}
