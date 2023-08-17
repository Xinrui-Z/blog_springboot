package myblogserver.controller;

import myblogserver.entity.Article;
import myblogserver.entity.Paper;
import myblogserver.service.PaperService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/paper")
@CrossOrigin
public class PaperController {

    @Autowired
    private PaperService paperService;

    /**
     * 添加博客
     * @param paper
     * @return
     */
    @PostMapping("/addpaper")
    public Mono<ResultVO> savePaper(@RequestBody Paper paper) {
        paper.setInsertTime(LocalDateTime.now());
        return paperService.addPaper(paper)
                .then(Mono.just(ResultVO.success("添加成功！")));
    }

    /**
     * 修改博客
     * @param paper
     * @return
     */
    @PostMapping("/paper")
    public Mono<ResultVO> postPaper(@RequestBody Paper paper) {
        return paperService.resetPaper(paper)
                .then(Mono.just(ResultVO.success("修改成功！")));
    }

    /**
     * 根据id删除博客
     * @param aid
     * @return
     */
    @DeleteMapping("/deletepaper/{aid}")
    public Mono<ResultVO> deletePaper(@PathVariable long aid) {
        return paperService.deletePaper(aid)
                .then(Mono.just(ResultVO.success("删除成功！")));
    }

    @PostMapping("/tagsPaper/{label}")
    public Mono<ResponseEntity<List<Paper>>> getPaperByLabel(@PathVariable String label) {
        Mono<List<Paper>> papers = paperService.getPaperByLabel(label);
        return papers.map(ResponseEntity::ok);
    }

}