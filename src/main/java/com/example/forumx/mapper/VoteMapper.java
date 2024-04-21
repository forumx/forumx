package com.example.forumx.mapper;

import com.example.forumx.entity.VoteEntity;
import com.example.forumx.model.VoteModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {
    public VoteEntity mapVoteModelToEntity(VoteModel voteModel) {
        return new ModelMapper().map(voteModel, VoteEntity.class);
    }

    public VoteModel mapVoteEntityToModel(VoteEntity voteEntity) {
        return new ModelMapper().map(voteEntity, VoteModel.class);
    }
}
