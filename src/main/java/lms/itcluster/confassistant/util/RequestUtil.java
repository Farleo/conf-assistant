package lms.itcluster.confassistant.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class RequestUtil {
	public static Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
		return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}

}
