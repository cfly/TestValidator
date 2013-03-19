package org.caofei;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.util.ValidatorUtils;

/**
 * バリデータ。
 * 
 * @author NTTDATA
 */
public class Validator {

	/** 日付パターン */
	private static final ThreadLocal<Pattern> DATE_PATTERN = new ThreadLocal<Pattern>();
	
	/** 本日からの相対値パターン */
	private static final ThreadLocal<Pattern> TODAY_PATTERN = new ThreadLocal<Pattern>();
	
	/** 日付フォーマット */
	private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>();

	/**
	 * 値が null や 空文字でないことを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateRequired(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		return !GenericValidator.isBlankOrNull(value);
	}

	/**
	 * 値が正規表現パターンとして正しいことを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateRegExp(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(value, field)) {
			return true;
		}

		String pattern = field.getVarValue("pattern");
		if (GenericValidator.isBlankOrNull(value)) {
			return GenericValidator.isBlankOrNull(pattern);
		}

		if (GenericValidator.isBlankOrNull(pattern)) {
			return false;
		}
		return value.matches(pattern);
	}

	/**
	 * 値が e-mail アドレスとして正しいことを確認する。
	 * 仕様により、半角英数、「@」、「.」、「-」、「_」のみで 構成されるかどうかでチェックする。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateEmail(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(value, field)) {
			return true;
		}
		return value.matches("^[a-zA-Z0-9._\\-]+@[a-zA-Z0-9._\\-]+$");
	}

	/**
	 * 値が整数として正しいことを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateInteger(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(value, field)) {
			return true;
		}

		if (value.equals("0")) {
			return true;
		}
		return value.matches("^\\-?[1-9][0-9]*$");
	}

	/**
	 * 数値が範囲内にあることを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateRange(Object bean, Field field) {
		String strValue = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(strValue, field)) {
			return true;
		}

		BigDecimal value = new BigDecimal(strValue);
		String min = field.getVarValue("min");
		if (min != null) {
			BigDecimal minValue = new BigDecimal(min);
			if (value.compareTo(minValue) < 0) {
				return false;
			}
		}

		String max = field.getVarValue("max");
		if (max != null) {
			BigDecimal maxValue = new BigDecimal(max);
			if (value.compareTo(maxValue) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列長が範囲内にあることを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateLength(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(value, field)) {
			return true;
		}

		int length = 0;
		if (value != null) {
			length = value.length();
		}

		String min = field.getVarValue("min");
		if (min != null) {
			int minLength = Integer.parseInt(field.getVarValue("min"));
			if (length < minLength) {
				return false;
			}
		}

		String max = field.getVarValue("max");
		if (max != null) {
			int maxLength = Integer.parseInt(field.getVarValue("max"));
			if (length > maxLength) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 日付文字列が有効な文字列であることを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateDate(Object bean, Field field) {
		String strDate = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(strDate, field)) {
			return true;
		}

		if (strDate == null) {
			return false;
		}

		if (!getDatePattern().matcher(strDate).matches()) {
			return false;
		}

		Date date = null;
		try {
			date = DateUtils.parseDate(strDate,"yyyy/MM/dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate.equals(getDateFormat().format(date));
	}

	/**
	 * 日付が範囲内にあることを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateDateRange(Object bean, Field field) {
		String strDate = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (isOptionalBlank(strDate, field)) {
			return true;
		}

		if (!validateDate(bean, field)) {
			return false;
		}

		String fromDate = getDateString(field.getVarValue("from"));
		if (!GenericValidator.isBlankOrNull(fromDate)) {
			if (strDate.compareTo(fromDate) < 0) {
				return false;
			}
		}

		String toDate = getDateString(field.getVarValue("to"));
		if (!GenericValidator.isBlankOrNull(toDate)) {
			if (strDate.compareTo(toDate) > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 項目数が範囲内にあることを確認する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return チェック結果
	 */
	public static boolean validateSelect(Object bean, Field field) {
		Object obj = null;
		try {
			obj = PropertyUtils.getProperty(bean, field.getProperty());
		} catch (Exception e) {
			return false;
		}

		if (obj == null) {
			return false;
		}

		int size;
		if (obj instanceof Object[]) {
			size = ((Object[]) obj).length;
		} else if (obj instanceof Collection<?>) {
			size = ((Collection<?>) obj).size();
		} else {
			return false;
		}

		String min = field.getVarValue("min");
		if (min != null) {
			int minLength = Integer.parseInt(field.getVarValue("min"));
			if (size < minLength) {
				return false;
			}
		}

		String max = field.getVarValue("max");
		if (max != null) {
			int maxLength = Integer.parseInt(field.getVarValue("max"));
			if (size > maxLength) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 確認対象フィールドの未設定を許可するか判定する。
	 * 
	 * @param bean コントロールオブジェクト
	 * @param field 確認対象フィールド
	 * @return 判定結果
	 */
	private static boolean isOptionalBlank(String value, Field field) {
		if ("true".equalsIgnoreCase(field.getVarValue("optional"))) {
			if (GenericValidator.isBlankOrNull(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 日付パターンを取得する。
	 * 
	 * @return 日付パターン
	 */
	private static Pattern getDatePattern() {
		Pattern datePattern = DATE_PATTERN.get();
		if (datePattern == null) {
			datePattern = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");
			DATE_PATTERN.set(datePattern);
		}
		return datePattern;
	}

	/**
	 * 本日からの相対値パターンを取得する。
	 * 
	 * @return 本日からの相対値パターン
	 */
	private static Pattern getTodayPattern() {
		Pattern todayPattern = TODAY_PATTERN.get();
		if (todayPattern == null) {
			todayPattern = Pattern.compile("^today\\s*(([+-])\\s*(\\d+))?$");
			TODAY_PATTERN.set(todayPattern);
		}
		return todayPattern;
	}

	/**
	 * 日付フォーマットを取得する。
	 * 
	 * @return 日付フォーマット(yyyy/MM/dd)
	 */
	private static SimpleDateFormat getDateFormat() {
		SimpleDateFormat dateFormat = DATE_FORMATTER.get();
		if (dateFormat == null) {
			dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DATE_FORMATTER.set(dateFormat);
		}
		return dateFormat;
	}

	/**
	 * 日付文字列を取得する。
	 * 
	 * @param date 日付
	 * @return 日付文字列(日付パターン)
	 */
	private static String getDateString(String date) {
		if (date == null) {
			return null;
		}

		Matcher matcher = getTodayPattern().matcher(date);
		if (!matcher.matches()) {
			return date;
		}

		// 日付が「today +/- n」で指定された場合
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()));
		String operator = matcher.group(2);
		String offset = matcher.group(3);
		if (operator != null && offset != null) {
			int iOffset = Integer.parseInt(offset);
			if (operator.equals("-")) {
				iOffset = iOffset * -1;
			}
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + iOffset);
		}
		return getDateFormat().format(calendar.getTime());
	}

}
