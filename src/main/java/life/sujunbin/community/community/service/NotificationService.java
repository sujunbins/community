package life.sujunbin.community.community.service;

import life.sujunbin.community.community.Mapper.NotificationMapper;
import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.enums.NotificationEnum;
import life.sujunbin.community.community.enums.NotificationStatucEnum;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import life.sujunbin.community.community.model.*;
import life.sujunbin.community.community.pojo.NotificationDTO;
import life.sujunbin.community.community.pojo.Pagintion;

import org.apache.ibatis.session.RowBounds;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author: 苏俊滨
 * @date: 2019/9/17 8:57
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    public Pagintion list(Long userId, Integer page, Integer size) {
        Pagintion<NotificationDTO> pagintion = new Pagintion();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);

        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        pagintion.setPagintion(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > pagintion.getTotalpage()) {
            page = pagintion.getTotalpage();
        }
        Integer offset = size * (page - 1);
        NotificationExample Example = new NotificationExample();
        Example.createCriteria().andReceiverEqualTo(userId);
        Example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(Example, new RowBounds(offset, size));
        System.out.println(notifications);
        if (notifications.size() == 0) {
            return pagintion;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        pagintion.setData(notificationDTOS);
        return pagintion;

    }

    public Long unreadCount(Long userid) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userid)
                .andStatucEqualTo(NotificationStatucEnum.UNREAD.gerStatuc());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification.getReceiver() == null) {
            throw new CustomizeException(CustomizeErrorcode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorcode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatuc(NotificationStatucEnum.READ.gerStatuc());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
