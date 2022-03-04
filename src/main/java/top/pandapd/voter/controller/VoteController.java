package top.pandapd.voter.controller;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Objects;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.pandapd.voter.analyzer.Selection;
import top.pandapd.voter.analyzer.Serialize;
import top.pandapd.voter.limit.IpUtil;
import top.pandapd.voter.limit.LoadingCacheServiceImpl;
import top.pandapd.voter.model.dto.VoteDetailDTO;
import top.pandapd.voter.model.po.VoteDetailPO;
import top.pandapd.voter.service.VoteService;
import top.pandapd.voter.tool.GetDate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Controller
public class VoteController {

    @Resource
    LoadingCacheServiceImpl loadingCacheService;

    @Resource
    private VoteService voteService;

    @RequestMapping("/vote/en/{vid}")
    public ModelAndView showVoteEN(@PathVariable Integer vid) {
        VoteDetailDTO voteDetailDTO = voteService.queryVote(vid);
        ModelAndView modelAndView = new ModelAndView("/vote/index_en");
        modelAndView.addObject("VoteID", voteDetailDTO.getVid());
        modelAndView.addObject("Title", voteDetailDTO.getTitle());
        modelAndView.addObject("Describe", voteDetailDTO.getDescription());
        modelAndView.addObject("Type", voteDetailDTO.getType());
        modelAndView.addObject("Limit", voteDetailDTO.getLimitation());
        //Selection process
        List<Map<String, String>> selects = Selection.analyze(voteDetailDTO.getSelection());
        modelAndView.addObject("Selection", selects);
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    @RequestMapping("/vote/cn/{vid}")
    public ModelAndView showVoteCN(@PathVariable Integer vid) {
        VoteDetailDTO voteDetailDTO = voteService.queryVote(vid);
        ModelAndView modelAndView = new ModelAndView("/vote/index_cn");
        modelAndView.addObject("VoteID", voteDetailDTO.getVid());
        modelAndView.addObject("Title", voteDetailDTO.getTitle());
        modelAndView.addObject("Describe", voteDetailDTO.getDescription());
        modelAndView.addObject("Type", voteDetailDTO.getType());
        modelAndView.addObject("Limit", voteDetailDTO.getLimitation());
        //Selection process
        List<Map<String, String>> selects = Selection.analyze(voteDetailDTO.getSelection());
        modelAndView.addObject("Selection", selects);
        modelAndView.addObject("YEAR", GetDate.year());
        return modelAndView;
    }

    @RequestMapping("/checkVoteID/{vid}")
    @ResponseBody
    public Integer checkVoteID(@PathVariable Integer vid) {
        if ( Objects.isNull(voteService.queryVote(vid)) ) {
            return 0;
        }
        return 1;
    }

    @RequestMapping("/voterSubmit")
    @ResponseBody
    public Integer voterSubmit(String title, String describe, String selection, Integer type, Integer limit) {
        //Verify
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(describe)) {
            return -7426;
        }
        if (limit < -1 || limit == 0) {
            return -7426;
        }
        //Update
        try {
            VoteDetailPO detailPO = new VoteDetailPO()
                .setTitle(title)
                .setDescription(describe)
                .setType(type)
                .setSelection(selection)
                .setLimitation(limit);
            return voteService.insertVote(detailPO);
        } catch (Exception E) {
            E.printStackTrace();
            return -1;
        }
    }

    @RequestMapping("/submitVote")
    @ResponseBody
    public String submitVote(HttpServletRequest request, Integer vid, String selected) {
        String ipAddr = IpUtil.getIpAddr(request);
        String ipAndVID = ipAddr + ":" + vid;
        try {
            RateLimiter limiter = loadingCacheService.getRateLimiter(ipAndVID);
            boolean localAccess = "0:0:0:0:0:0:0:1".equals(ipAddr);
            //Localhost is in the WhiteList
            if (limiter.tryAcquire() || localAccess) {
                //PASS
                //Get VID info
                VoteDetailDTO voteDetailDTO = voteService.queryVote(vid);
                String selectionSerial = voteDetailDTO.getSelection();
                //Package and readout
                List<Map<String, String>> selects = Selection.analyze(selectionSerial);
                //Split selection
                String[] selectedList = selected.split(",");
                //Convert to Integer
                Integer[] selList = new Integer[selectedList.length];
                int i = 0;
                for (String sel : selectedList) {
                    selList[i] = Integer.parseInt(sel);
                    ++i;
                }
                //Write changes
                //Generate new List
                List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
                //Change one by one
                for (Map<String, String> stringMap : selects) {
                    boolean flag = false;
                    //Submit
                    for (Integer sel : selList) {
                        if (Integer.parseInt(stringMap.get("num")) == sel) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        //Change
                        int before = Integer.parseInt(stringMap.get("count"));
                        Integer after = before + 1;
                        stringMap.put("count", String.valueOf(after));
                    }
                    newList.add(stringMap);
                }
                //To serial
                String serial = Serialize.makeSerial(newList);
                //Update to database
                VoteDetailPO po = new VoteDetailPO().setSelection(serial).setVid(vid);
                Integer updateCnt = voteService.updateVote(po);
                return updateCnt.toString();
            } else {
                //DENIED
                return "0";
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
