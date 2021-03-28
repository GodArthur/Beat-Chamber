package com.beatchamber.beans.manager;

import com.beatchamber.jpacontroller.OrderAlbumJpaController;
import com.beatchamber.jpacontroller.OrderTrackJpaController;
import com.beatchamber.jpacontroller.OrdersJpaController;
import java.io.Serializable;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *This class will be used as the bean for the order management page
 * @author Massimo Di Girolamo
 */
public class OrderBean implements Serializable {
    
    private final static Logger LOG = LoggerFactory.getLogger(OrderBean.class);

    @Inject
    private OrdersJpaController ordersJpaController;
    
    @Inject
    private OrderAlbumJpaController orderAlbumJpaController;
    
    @Inject
    private OrderTrackJpaController orderTrackJpaController;
    
    
}
