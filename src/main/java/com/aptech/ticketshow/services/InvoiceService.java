package com.aptech.ticketshow.services;

import com.aptech.ticketshow.data.dtos.OrderDTO;
import com.aptech.ticketshow.data.dtos.UserDTO;

public interface InvoiceService {

    void createInvoiceWithPdfAndEmail(OrderDTO orderDTO);
}
