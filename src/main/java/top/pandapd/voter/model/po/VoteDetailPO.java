package top.pandapd.voter.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 投票实体
 * @author Pandapd
 * @date Created at 2022/2/28 17:35
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@TableName("vote_detail")
public class VoteDetailPO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer vid;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 选项及票数序列化字符串
     */
    private String selection;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 限制
     */
    private Integer limitation;

    /**
     * 是否逻辑删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
