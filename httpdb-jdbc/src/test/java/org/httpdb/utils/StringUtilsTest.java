package org.httpdb.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link StringUtils}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@DisplayName("StringUtils")
class StringUtilsTest {

    @Nested
    @DisplayName("isEmpty")
    class IsEmpty {

        @Test
        @DisplayName("should return true for null")
        void shouldReturnTrueForNull() {
            assertThat(StringUtils.isEmpty(null)).isTrue();
        }

        @Test
        @DisplayName("should return true for empty string")
        void shouldReturnTrueForEmpty() {
            assertThat(StringUtils.isEmpty("")).isTrue();
        }

        @Test
        @DisplayName("should return false for whitespace")
        void shouldReturnFalseForWhitespace() {
            assertThat(StringUtils.isEmpty(" ")).isFalse();
        }

        @Test
        @DisplayName("should return false for non-empty string")
        void shouldReturnFalseForNonEmpty() {
            assertThat(StringUtils.isEmpty("hello")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNotEmpty")
    class IsNotEmpty {

        @Test
        @DisplayName("should return false for null")
        void shouldReturnFalseForNull() {
            assertThat(StringUtils.isNotEmpty(null)).isFalse();
        }

        @Test
        @DisplayName("should return false for empty string")
        void shouldReturnFalseForEmpty() {
            assertThat(StringUtils.isNotEmpty("")).isFalse();
        }

        @Test
        @DisplayName("should return true for non-empty string")
        void shouldReturnTrueForNonEmpty() {
            assertThat(StringUtils.isNotEmpty("hello")).isTrue();
        }
    }

    @Nested
    @DisplayName("isAnyEmpty")
    class IsAnyEmpty {

        @Test
        @DisplayName("should return true for null array")
        void shouldReturnTrueForNullArray() {
            assertThat(StringUtils.isAnyEmpty((CharSequence[]) null)).isTrue();
        }

        @Test
        @DisplayName("should return true if any element is empty")
        void shouldReturnTrueIfAnyEmpty() {
            assertThat(StringUtils.isAnyEmpty("a", "", "b")).isTrue();
        }

        @Test
        @DisplayName("should return true if any element is null")
        void shouldReturnTrueIfAnyNull() {
            assertThat(StringUtils.isAnyEmpty("a", null, "b")).isTrue();
        }

        @Test
        @DisplayName("should return false if all elements are non-empty")
        void shouldReturnFalseIfAllNonEmpty() {
            assertThat(StringUtils.isAnyEmpty("a", "b", "c")).isFalse();
        }
    }

    @Nested
    @DisplayName("isNoneEmpty")
    class IsNoneEmpty {

        @Test
        @DisplayName("should return false for null array")
        void shouldReturnFalseForNullArray() {
            assertThat(StringUtils.isNoneEmpty((CharSequence[]) null)).isFalse();
        }

        @Test
        @DisplayName("should return false if any element is empty")
        void shouldReturnFalseIfAnyEmpty() {
            assertThat(StringUtils.isNoneEmpty("a", "", "b")).isFalse();
        }

        @Test
        @DisplayName("should return true if all elements are non-empty")
        void shouldReturnTrueIfAllNonEmpty() {
            assertThat(StringUtils.isNoneEmpty("a", "b", "c")).isTrue();
        }
    }

    @Nested
    @DisplayName("tokenizeToStringArray")
    class TokenizeToStringArray {

        @Test
        @DisplayName("should tokenize with default delimiters")
        void shouldTokenizeWithDefaultDelimiters() {
            String[] tokens = StringUtils.tokenizeToStringArray("a,b;c d");
            assertThat(tokens).containsExactly("a", "b", "c", "d");
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNull() {
            assertThat(StringUtils.tokenizeToStringArray(null)).isNull();
        }

        @Test
        @DisplayName("should trim tokens by default")
        void shouldTrimTokensByDefault() {
            String[] tokens = StringUtils.tokenizeToStringArray(" a , b ");
            assertThat(tokens).containsExactly("a", "b");
        }

        @Test
        @DisplayName("should ignore empty tokens by default")
        void shouldIgnoreEmptyTokens() {
            String[] tokens = StringUtils.tokenizeToStringArray("a,,b");
            assertThat(tokens).containsExactly("a", "b");
        }

        @Test
        @DisplayName("should tokenize with custom delimiters")
        void shouldTokenizeWithCustomDelimiters() {
            String[] tokens = StringUtils.tokenizeToStringArray("a|b|c", "|");
            assertThat(tokens).containsExactly("a", "b", "c");
        }
    }

    @Nested
    @DisplayName("toStringArray")
    class ToStringArray {

        @Test
        @DisplayName("should convert collection to array")
        void shouldConvertCollectionToArray() {
            java.util.List<String> list = java.util.Arrays.asList("a", "b", "c");
            String[] arr = StringUtils.toStringArray(list);
            assertThat(arr).containsExactly("a", "b", "c");
        }

        @Test
        @DisplayName("should return null for null collection")
        void shouldReturnNullForNull() {
            assertThat(StringUtils.toStringArray(null)).isNull();
        }
    }

    @Nested
    @DisplayName("toZeroPaddedString")
    class ToZeroPaddedString {

        @Test
        @DisplayName("should pad with zeros")
        void shouldPadWithZeros() {
            assertThat(StringUtils.toZeroPaddedString(5, 3, 3)).isEqualTo("005");
        }

        @Test
        @DisplayName("should handle negative values as positive")
        void shouldHandleNegativeValues() {
            assertThat(StringUtils.toZeroPaddedString(-5, 3, 3)).isEqualTo("005");
        }

        @Test
        @DisplayName("should trim to maxSize when smaller than precision")
        void shouldTrimToMaxSize() {
            assertThat(StringUtils.toZeroPaddedString(12345, 5, 3)).isEqualTo("123");
        }
    }

    @Nested
    @DisplayName("hexStringToByteArray")
    class HexStringToByteArray {

        @Test
        @DisplayName("should convert hex string to byte array")
        void shouldConvertHexString() throws IOException {
            byte[] result = StringUtils.hexStringToByteArray("0a1b2c");
            assertThat(result).containsExactly(0x0a, 0x1b, 0x2c);
        }

        @Test
        @DisplayName("should handle uppercase hex")
        void shouldHandleUppercaseHex() throws IOException {
            byte[] result = StringUtils.hexStringToByteArray("0A1B");
            assertThat(result).containsExactly(0x0a, 0x1b);
        }

        @Test
        @DisplayName("should skip spaces in hex string")
        void shouldSkipSpaces() throws IOException {
            byte[] result = StringUtils.hexStringToByteArray("0a 1b 2c");
            assertThat(result).containsExactly(0x0a, 0x1b, 0x2c);
        }

        @Test
        @DisplayName("should throw on invalid hex character")
        void shouldThrowOnInvalidHex() {
            assertThatThrownBy(() -> StringUtils.hexStringToByteArray("xyz"))
                    .isInstanceOf(IOException.class);
        }

        @Test
        @DisplayName("should throw on odd-length hex string")
        void shouldThrowOnOddLength() {
            assertThatThrownBy(() -> StringUtils.hexStringToByteArray("0a1"))
                    .isInstanceOf(IOException.class);
        }
    }

    @Nested
    @DisplayName("byteArrayToHexString")
    class ByteArrayToHexString {

        @Test
        @DisplayName("should convert byte array to hex string")
        void shouldConvertByteArray() {
            String result = StringUtils.byteArrayToHexString(new byte[]{0x0a, 0x1b, 0x2c});
            assertThat(result).isEqualTo("0a1b2c");
        }

        @Test
        @DisplayName("should handle empty array")
        void shouldHandleEmptyArray() {
            assertThat(StringUtils.byteArrayToHexString(new byte[]{})).isEmpty();
        }
    }

    @Nested
    @DisplayName("byteArrayToSQLHexString")
    class ByteArrayToSQLHexString {

        @Test
        @DisplayName("should wrap hex in X'' format")
        void shouldWrapInSqlFormat() {
            String result = StringUtils.byteArrayToSQLHexString(new byte[]{0x0a, 0x1b});
            assertThat(result).isEqualTo("X'0a1b'");
        }
    }
}
