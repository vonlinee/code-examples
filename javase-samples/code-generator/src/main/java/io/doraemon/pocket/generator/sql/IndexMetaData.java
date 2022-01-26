package io.doraemon.pocket.generator.sql;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Index meta data.
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public final class IndexMetaData {
    
    private final String name;
}
