//package com.aptech.ticketshow.services.impl;
//
//import com.aptech.ticketshow.data.dtos.OrganiserDTO;
//import com.aptech.ticketshow.data.entities.Organiser;
//import com.aptech.ticketshow.data.mappers.OrganiserMapper;
//import com.aptech.ticketshow.data.mappers.UserMapper;
//import com.aptech.ticketshow.data.repositories.OrganiserRepository;
//import com.aptech.ticketshow.services.OrganiserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class OrganiserServiceImpl implements OrganiserService {
//
//  @Autowired
//  private OrganiserRepository organiserRepository;
//
//  @Autowired
//  private OrganiserMapper organiserMapper;
//
//  @Autowired
//  private UserMapper userMapper;
//
//  @Override
//  public List<OrganiserDTO> findAll() {
//    return organiserRepository.findAll().stream().map(r -> organiserMapper.toDTO(r)).collect(Collectors.toList());
//  }
//
//  @Override
//  public OrganiserDTO findById(Long id) {
//    Optional<Organiser> organiserOptional = organiserRepository.findById(id);
//    if (organiserOptional.isPresent()) {
//      return organiserMapper.toDTO(organiserOptional.get());
//    } else {
//      throw new RuntimeException("Organiser not found with id: " + id);
//    }
//  }
//
//  @Override
//  public OrganiserDTO create(OrganiserDTO organiserDTO) {
//    Organiser organiser = organiserMapper.toEntity(organiserDTO);
//    organiser = organiserRepository.save(organiser);
//    return organiserMapper.toDTO(organiser);
//  }
//
////  @Override
////  public OrganiserDTO update(OrganiserDTO organiserDTO) {
////    Optional<Organiser> organiserOptional = organiserRepository.findById(organiserDTO.getId());
////    if (organiserOptional.isPresent()) {
////      Organiser organiser = organiserOptional.get();
////      organiser.setUser(userMapper.toEntity(organiserDTO.getUserDTO()));
////      organiser.setName(organiserDTO.getName());
////      organiser.setDescription(organiserDTO.getDescription());
////      organiser.setIndividualName(organiserDTO.getIndividualName());
////      organiser.setIndividualTax(organiserDTO.getIndividualTax());
////      organiser.setIndividualIdentify(organiserDTO.getIndividualIdentify());
////      organiser.setIndividualEmail(organiserDTO.getIndividualEmail());
////      organiser.setIndividualAddress(organiserDTO.getBusinessAddress());
////      organiser.setIndividualDistrict(organiserDTO.getIndividualDistrict());
////      organiser.setIndividualWard(organiserDTO.getIndividualWard());
////      organiser.setBusinessName(organiserDTO.getBusinessName());
////      organiser.setBusinessTax(organiserDTO.getBusinessTax());
////      organiser.setBusinessIssuePlace(organiserDTO.getBusinessIssuePlace());
////      organiser.setBusinessIssueDate(organiserDTO.getBusinessIssueDate());
////      organiser.setBusinessPhone(organiserDTO.getBusinessPhone());
////      organiser.setBusinessEmail(organiserDTO.getBusinessEmail());
////      organiser.setBusinessAddress(organiserDTO.getBusinessAddress());
////      organiser.setBusinessProvince(organiserDTO.getBusinessProvince());
////      organiser.setBusinessDistrict(organiserDTO.getBusinessDistrict());
////      organiser.setBusinessWard(organiserDTO.getBusinessWard());
////      organiser.setBalance(organiserDTO.getBalance());
////      organiser.setDefaultBankId(organiserDTO.getDefaultBankId());
////      organiser = organiserRepository.save(organiser);
////      return organiserMapper.toDTO(organiser);
////    } else {
////      throw new RuntimeException("Organiser not found with id: " + organiserDTO.getId());
////    }
////  }
//
//  @Override
//  public void delete(Long id) {
//    Optional<Organiser> organiserOptional = organiserRepository.findById(id);
//    if (organiserOptional.isPresent()) {
//      organiserRepository.deleteById(id);
//    } else {
//      throw new RuntimeException("Organiser not found with id: " + id);
//    }
//  }
//}
