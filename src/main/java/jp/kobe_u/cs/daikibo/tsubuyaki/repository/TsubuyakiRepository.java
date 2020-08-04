package jp.kobe_u.cs.daikibo.tsubuyaki.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;

@Repository
public interface TsubuyakiRepository extends CrudRepository<Tsubuyaki, Long> {
    public Iterable<Tsubuyaki> findByCommentContains( String searchWord );

    // @Query( "select t from Tsubuyaki t where t.comment like %?1%" )
    // public Iterable<Tsubuyaki> findByLikeComment( String searchWord );
}