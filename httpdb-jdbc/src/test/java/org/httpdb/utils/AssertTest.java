package org.httpdb.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link Assert}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@DisplayName("Assert")
class AssertTest {

    @Nested
    @DisplayName("isTrue")
    class IsTrue {

        @Test
        @DisplayName("should not throw when expression is true")
        void shouldNotThrowWhenTrue() {
            assertThatCode(() -> Assert.isTrue(true)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw when expression is false")
        void shouldThrowWhenFalse() {
            assertThatThrownBy(() -> Assert.isTrue(false))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should use custom message")
        void shouldUseCustomMessage() {
            assertThatThrownBy(() -> Assert.isTrue(false, "custom"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("custom");
        }
    }

    @Nested
    @DisplayName("isNull")
    class IsNull {

        @Test
        @DisplayName("should not throw for null")
        void shouldNotThrowForNull() {
            assertThatCode(() -> Assert.isNull(null)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw for non-null")
        void shouldThrowForNonNull() {
            assertThatThrownBy(() -> Assert.isNull("x"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("notNull")
    class NotNull {

        @Test
        @DisplayName("should not throw for non-null")
        void shouldNotThrowForNonNull() {
            assertThatCode(() -> Assert.notNull("x")).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw for null")
        void shouldThrowForNull() {
            assertThatThrownBy(() -> Assert.notNull(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should use custom message")
        void shouldUseCustomMessage() {
            assertThatThrownBy(() -> Assert.notNull(null, "must not be null"))
                    .hasMessage("must not be null");
        }
    }

    @Nested
    @DisplayName("hasLength")
    class HasLength {

        @Test
        @DisplayName("should not throw for non-empty string")
        void shouldNotThrowForNonEmpty() {
            assertThatCode(() -> Assert.hasLength("hello")).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw for null string")
        void shouldThrowForNull() {
            assertThatThrownBy(() -> Assert.hasLength(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should throw for empty string")
        void shouldThrowForEmpty() {
            assertThatThrownBy(() -> Assert.hasLength(""))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("notEmpty (array)")
    class NotEmptyArray {

        @Test
        @DisplayName("should throw for non-empty array (bug in original)")
        void shouldThrowForNonEmptyArray() {
            // Note: original code has inverted logic - throws when array is NOT empty
            assertThatThrownBy(() -> Assert.notEmpty(new Object[]{"a"}))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should not throw for empty array (bug in original)")
        void shouldNotThrowForEmptyArray() {
            assertThatCode(() -> Assert.notEmpty(new Object[]{})).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("noNullElements")
    class NoNullElements {

        @Test
        @DisplayName("should not throw for array without nulls")
        void shouldNotThrowWithoutNulls() {
            assertThatCode(() -> Assert.noNullElements(new Object[]{"a", "b"}))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw for array with null element")
        void shouldThrowWithNull() {
            assertThatThrownBy(() -> Assert.noNullElements(new Object[]{"a", null}))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should not throw for null array")
        void shouldNotThrowForNullArray() {
            assertThatCode(() -> Assert.noNullElements(null)).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("doesNotContain")
    class DoesNotContain {

        @Test
        @DisplayName("should not throw when substring is not present")
        void shouldNotThrowWhenNotPresent() {
            assertThatCode(() -> Assert.doesNotContain("hello", "xyz"))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw when substring is present")
        void shouldThrowWhenPresent() {
            assertThatThrownBy(() -> Assert.doesNotContain("hello", "ell"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("state")
    class State {

        @Test
        @DisplayName("should not throw when state is true")
        void shouldNotThrowWhenTrue() {
            assertThatCode(() -> Assert.state(true)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw IllegalStateException when false")
        void shouldThrowWhenFalse() {
            assertThatThrownBy(() -> Assert.state(false))
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @Nested
    @DisplayName("isInstanceOf")
    class IsInstanceOf {

        @Test
        @DisplayName("should not throw for correct type")
        void shouldNotThrowForCorrectType() {
            assertThatCode(() -> Assert.isInstanceOf(String.class, "hello"))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw for wrong type")
        void shouldThrowForWrongType() {
            assertThatThrownBy(() -> Assert.isInstanceOf(Integer.class, "hello"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("isAssignable")
    class IsAssignable {

        @Test
        @DisplayName("should not throw for assignable types")
        void shouldNotThrowForAssignable() {
            assertThatCode(() -> Assert.isAssignable(Number.class, Integer.class))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("should throw for non-assignable types")
        void shouldThrowForNonAssignable() {
            assertThatThrownBy(() -> Assert.isAssignable(String.class, Integer.class))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
