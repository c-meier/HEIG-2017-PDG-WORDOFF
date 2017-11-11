package ch.heigvd.wordoff.server.Util;

import ch.heigvd.wordoff.common.Dto.AnswerDto;
import ch.heigvd.wordoff.common.Dto.Tiles.TileDto;
import ch.heigvd.wordoff.server.Model.Answer;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.ParseException;

/**
 * Project : WordOff
 * Date : 10.11.17
 */
public class DaoConveter {
    //    private PostDto convertToDto(Post post) {
//        PostDto postDto = modelMapper.map(post, PostDto.class);
//        postDto.setSubmissionDate(post.getSubmissionDate(),
//                userService.getCurrentUser().getPreference().getTimezone());
//        return postDto;
//    }
//
//    private Post convertToEntity(PostDto postDto) throws ParseException {
//        Post post = modelMapper.map(postDto, Post.class);
//        post.setSubmissionDate(postDto.getSubmissionDateConverted(
//                userService.getCurrentUser().getPreference().getTimezone()));
//
//        if (postDto.getId() != null) {
//            Post oldPost = postService.getPostById(postDto.getId());
//            post.setRedditID(oldPost.getRedditID());
//            post.setSent(oldPost.isSent());
//        }
//        return post;
//    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static TileDto convertToDto(Tile tile) {
        TileDto tileDto = modelMapper.map(tile, TileDto.class);
        return tileDto;
    }

}
