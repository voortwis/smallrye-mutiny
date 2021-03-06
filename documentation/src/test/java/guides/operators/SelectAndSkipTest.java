package guides.operators;

import io.smallrye.mutiny.Multi;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectAndSkipTest {

    @Test
    public void testSelect() {
        Multi<Integer> multi = Multi.createFrom().items(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // tag::take-first[]
        Multi<Integer> firstThreeItems = multi.select().first(3);
        // end::take-first[]

        // tag::take-last[]
        Multi<Integer> lastThreeItems = multi.select().last(3);
        // end::take-last[]

        // tag::take-while[]
        Multi<Integer> takeWhile = multi.select().first(i -> i < 4);
        // end::take-while[]

        // tag::take-for[]
        Multi<Integer> takeForDuration = multi.select().first(Duration.ofSeconds(1));
        // end::take-for[]

        assertThat(firstThreeItems.collect().asList().await().indefinitely()).containsExactly(1, 2, 3);
        assertThat(takeWhile.collect().asList().await().indefinitely()).containsExactly(1, 2, 3);
        assertThat(lastThreeItems.collect().asList().await().indefinitely()).containsExactly(7, 8, 9);
        assertThat(takeForDuration.collect().asList().await().indefinitely()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);

    }

    @Test
    public void testSkip() {
        Multi<Integer> multi = Multi.createFrom().items(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // tag::skip-first[]
        Multi<Integer> skipThreeItems = multi.skip().first(3);
        // end::skip-first[]

        // tag::skip-last[]
        Multi<Integer>  skipLastThreeItems = multi.skip().last(3);
        // end::skip-last[]

        // tag::skip-while[]
        Multi<Integer> skipWhile = multi.skip().first(i -> i < 4);
        // end::skip-while[]

        // tag::skip-for[]
        Multi<Integer> skipForDuration = multi.skip().first(Duration.ofSeconds(1));
        // end::skip-for[]

        assertThat(skipThreeItems.collect().asList().await().indefinitely()).containsExactly(4, 5, 6, 7, 8, 9);
        assertThat(skipLastThreeItems.collect().asList().await().indefinitely()).containsExactly(1, 2, 3, 4 ,5, 6);
        assertThat(skipWhile.collect().asList().await().indefinitely()).containsExactly(4, 5, 6, 7, 8, 9);
        assertThat(skipForDuration.collect().asList().await().indefinitely()).isEmpty();

    }
}
