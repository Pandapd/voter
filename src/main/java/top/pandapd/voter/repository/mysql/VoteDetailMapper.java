package top.pandapd.voter.repository.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pandapd.voter.model.po.VoteDetailPO;

/**
 *
 * @author Pandapd
 * @date Created at 2022/2/28 17:55
 */
@Mapper
public interface VoteDetailMapper extends BaseMapper<VoteDetailPO> {

}
