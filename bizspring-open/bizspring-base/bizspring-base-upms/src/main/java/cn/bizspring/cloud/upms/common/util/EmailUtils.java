package cn.bizspring.cloud.upms.common.util;

import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Configuration
@AllArgsConstructor
public class EmailUtils {
	private final EmailConfigProperties emailConfigProperties;

	public void sendEmail(String to, String title, String content) throws SendMailException {
		try {
			OhMyEmail.subject(title)
					.from(emailConfigProperties.getSiteName())
					.to(to)
					.html(content)
					.attachURL(new URL("https://qiniuio.bizspring.cn/BizSpring.png"), "BizSpring.png")
					.send();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		log.info("email: {} send success", to);
	}
}
