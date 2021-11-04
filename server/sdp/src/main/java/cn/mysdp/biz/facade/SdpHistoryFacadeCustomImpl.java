package cn.mysdp.biz.facade;

import cn.mysdp.biz.domain.SdpHistoryExample;
import cn.mysdp.biz.dto.request.BaseRequest;

/**
 * @ClassName:
 * @Description: FacadeCustomImpl
 * @Author: SDP
 * @Date: 2021-10-30
 * @Version: 1.0
 * Table:
 * Comment:
 *
 */
public class SdpHistoryFacadeCustomImpl extends SdpHistoryFacadeBaseImpl {

    @Override
    public int clear(BaseRequest request) throws Exception {
        SdpHistoryExample example = new SdpHistoryExample();

        return sdpHistoryMapper.deleteByExample(example);
    }
}
