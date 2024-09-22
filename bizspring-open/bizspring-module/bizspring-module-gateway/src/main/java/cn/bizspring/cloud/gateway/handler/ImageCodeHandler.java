package cn.bizspring.cloud.gateway.handler;

import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * 验证码生成逻辑处理类
 */
@Slf4j
@Component
@AllArgsConstructor
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {
	private static final String JPEG = "jpeg";
	private final RedisTemplate redisTemplate;

	@Override
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		//生成验证码
		String text = "";
		BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.createGraphics();
		//定义颜色
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 200, 100);
		//产生4个随机数
		ArrayList<Integer> list = new ArrayList<Integer>();
		Random random = new Random();
		for(int i = 0; i < 4; i++) {
			int randint= random.nextInt(10);
			list.add(randint);
			text+=randint;
		}
		//设置字体 宋体  加粗 大小为50
		graphics.setFont(new Font("宋体", Font.BOLD, 55));
		//定义一个颜色数组
		Color[] colors = new Color[]{Color.blue, Color.BLACK, Color.GREEN, Color.RED, Color.ORANGE};
		for(int i = 0; i < list.size(); i++) {
			//使用随机颜色
			graphics.setColor(colors[random.nextInt(colors.length)]);
			//画字符 并使字符高度随机
			graphics.drawString("" + list.get(i), i * 50 + 5, 50 + random.nextInt(51) - 10);
		}
		//画上三条随机的线
		for(int i = 0; i < 3; i++) {
			graphics.setColor(colors[random.nextInt(colors.length)]);
			graphics.drawLine(0, random.nextInt(101), 200, random.nextInt(101));
		}
		//保存验证码信息
		String randomStr = serverRequest.queryParam("randomStr").get();
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		redisTemplate.opsForValue().set(CacheEnum.VER_CODE_DEFAULT + randomStr, text
			, SecurityEnum.CODE_TIME, TimeUnit.SECONDS);
		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		try {
			ImageIO.write(image, JPEG, os);
		} catch (IOException e) {
			log.error("ImageIO write err", e);
			return Mono.error(e);
		}
		os.close();
		return ServerResponse
			.status(HttpStatus.OK)
			.contentType(MediaType.IMAGE_JPEG)
			.body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
	}
}
