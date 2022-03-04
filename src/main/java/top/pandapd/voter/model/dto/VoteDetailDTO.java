package top.pandapd.voter.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author Pandapd
 * @date Created at 2022/2/28 18:06
 */
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class VoteDetailDTO {
    /**
     * id
     */
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
