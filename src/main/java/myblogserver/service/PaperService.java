package myblogserver.service;

import myblogserver.entity.Paper;
import myblogserver.exception.XException;
import myblogserver.repository.PaperRepository;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    public Mono<Paper> addPaper(Paper paper) {
        Mono<Paper> paperM = paperRepository.findFirstByLabel(paper.getLabel());
       return paperRepository.findCountByLabel(paper.getLabel())
               .filter(r -> r != 0)
               .flatMap(r -> paperM.flatMap(a -> paperRepository.updateLabelCountByLabel(paper.getLabel(), a.getLabelCount()+1)))
               .flatMap(r -> paperM.flatMap(a -> {
                   paper.setLabelCount(a.getLabelCount());
                   return paperRepository.save(paper);
               }))
               .switchIfEmpty(paperRepository.save(paper));
    }

    public Mono<List<Paper>> listPapers(int page, int pageSize) {
        return paperRepository.findAll((page-1)*pageSize, pageSize).collectList();
    }

    public Mono<Integer> listPapersCount() {
        return paperRepository.findCount();
    }

    public Mono<Void> resetPaper(Paper paper) {
        return paperRepository.updatePaperById(paper.getLabel(), paper.getTitle(), paper.getAuthor(), paper.getContent(), paper.getId())
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }

    public Mono<Paper> getPaperById(long aid) {
        return paperRepository.findById(aid);
    }

    public Mono<List<Paper>> getPaperByLabel(String label) {
        return paperRepository.findByLabel(label).collectList();
    }

    @Transactional
    public Mono<Void> deletePaper(long aid) {
        Mono<Paper> paperM = paperRepository.findById(aid);
        return paperM.flatMap(paper -> paperRepository.updateLabelCountByLabel(
                paper.getLabel(), paper.getLabelCount()-1
        )).then(paperRepository.deleteById(aid).then());
    }

    public Mono<List<Paper>> listLabelsAndCount() {
        return paperRepository.findLabelsAndCount().collectList();
    }
}
