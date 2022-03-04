package top.pandapd.voter.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.pandapd.voter.model.dto.VoteDetailDTO;
import top.pandapd.voter.model.po.VoteDetailPO;

/**
 * vote 相关DTO转换器
 * @author Pandapd
 * @date Created at 2022/2/28 18:34
 */
@Mapper
public interface VoteConverter {
    VoteConverter INSTANCE = Mappers.getMapper(VoteConverter.class);

    /**
     * VoteDetailDTO  与 VoteDetailPO相互转换
     */
    VoteDetailDTO toVoteDetailDTO(VoteDetailPO voteDetailPO);
}
