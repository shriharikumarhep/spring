package com.org.onehome.user.controller;

import com.org.onehome.login.model.UserModel;
import org.apache.catalina.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class UserRepresentationModel implements RepresentationModelAssembler<UserModel, EntityModel<UserModel>> {
    @Override
    public EntityModel<UserModel> toModel(UserModel entity) {

        EntityModel<UserModel> orderModel = EntityModel.of(entity,
                linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return orderModel;
    }

    @Override
    public CollectionModel<EntityModel<UserModel>> toCollectionModel(Iterable<? extends UserModel> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
