//package com.aptech.ticketshow.data.mappers;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import com.aptech.ticketshow.data.dtos.FavoriteDTO;
//import com.aptech.ticketshow.data.entities.Favorite;
//
//@Mapper(componentModel = "spring", uses = {
//        UserMapper.class, EventMapper.class
//})
//public interface FavoriteMapper {
//
//	@Mapping(source = "eventDTO", target = "event")
//	@Mapping(source = "userDTO", target = "user")
//	Favorite toEntity(FavoriteDTO favoriteDTO);
//	
//	@Mapping(source = "event", target = "eventDTO")
//	@Mapping(source = "user", target = "userDTO")
//	FavoriteDTO toDTO(Favorite favorite);
//}
