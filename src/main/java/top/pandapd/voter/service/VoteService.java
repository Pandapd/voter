package top.pandapd.voter.service;

import top.pandapd.voter.model.dto.VoteDetailDTO;
import top.pandapd.voter.model.po.VoteDetailPO;

/**
 * vote服务接口
 * @author Pandapd
 * @date Created at 2022/2/28 18:04
 */

public interface VoteService {
    VoteDetailDTO queryVote(Integer vid);

    Integer insertVote(VoteDetailPO po);

    Integer updateVote(VoteDetailPO po);
}
