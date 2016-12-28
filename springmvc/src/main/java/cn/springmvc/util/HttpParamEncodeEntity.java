/**
 * 
 */
package cn.springmvc.util;

import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.util.Assert;

/**
 * @author ready
 * 
 */
public class HttpParamEncodeEntity extends StringEntity {

	public HttpParamEncodeEntity(List<NameValuePair> formParams, String charset)
			throws Exception, Exception {
		super(ParamEncodeUtil.format(formParams, charset), ContentType.create(
				URLEncodedUtils.CONTENT_TYPE, charset));
	}

	private static class ParamEncodeUtil {

		static String format(List<NameValuePair> formParams,
				String requestCharacter) throws Exception {
			StringBuffer sb = new StringBuffer();
			Assert.notEmpty(formParams);
			for (NameValuePair nv : formParams) {
				if (sb.length() > 0) {
					sb.append("&");
				}
				sb.append(encodeString(nv.getName(), requestCharacter));
				sb.append("=");
				sb.append(encodeString(nv.getValue(), requestCharacter));
			}

			return sb.toString();
		}

		private static String encodeString(String str, String requestCharacter)
				throws Exception {
			return URLEncoder.encode(str, requestCharacter);
		}

	}

}
