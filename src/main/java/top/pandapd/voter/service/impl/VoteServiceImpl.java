package top.pandapd.voter.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pandapd.voter.convert.VoteConverter;
import top.pandapd.voter.model.dto.VoteDetailDTO;
import top.pandapd.voter.model.po.VoteDetailPO;
import top.pandapd.voter.repository.mysql.VoteDetailMapper;
import top.pandapd.voter.service.VoteService;

/**
 *
 * @author Pandapd
 * @date Created at 2022/2/28 18:15
 */

@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    @Resource
    private VoteDetailMapper voteDetailMapper;

    @Override
    public VoteDetailDTO queryVote(Integer vid) {
        VoteDetailPO voteDetailPO = voteDetailMapper.selectById(vid);
        return VoteConverter.INSTANCE.toVoteDetailDTO(voteDetailPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertVote(VoteDetailPO po) {
        voteDetailMapper.insert(po);
        return po.getVid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateVote(VoteDetailPO po) {
        return voteDetailMapper.updateById(po);
    }
}
