package net.anweisen.gommestats.manager.clans;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.InteractivePage;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import net.anweisen.gommestats.manager.ConnectionManager;
import org.apache.http.util.Asserts;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;
import sun.awt.SunToolkit.OperationTimedOut;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * @author anweisen
 * GommeBot developed on 07-31-2020
 * https://github.com/anweisen
 */

public class ClanWrapper {

	public static int getActiveClansWithException() throws IOException {

		String request = "https://www.gommehd.net/clans";
		Document document = ConnectionManager.openConnection(request);

		Element element = document.getElementById("total-clans");
		String text = element.text().replace(".", "");

		return Integer.parseInt(text);

	}

	public static Clan getClanWithException(String clanName) throws IOException, OperationTimedOut, InterruptedException {

		String request = Clan.getURL(clanName);

		System.out.println("Creating client");
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
		webClient.setCssErrorHandler(CSS_ERROR_HANDLER);
		webClient.setJavaScriptErrorListener(JAVA_SCRIPT_ERROR_LISTENER);
		webClient.setJavaScriptTimeout(5000);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);

		System.out.println("Opening page");
		HtmlPage page = webClient.getPage(request);
		System.out.println("Opened page");

		System.out.println("Started waiting");
		long start = System.currentTimeMillis();
		while (iterableSize(page.getElementById("clanMembersList").getChildElements()) < 4) {
			// Waiting for javascript to finish its job
			// Ending waiting after 7.5 seconds
			Thread.sleep(100);
			if ((start+7.5*1000) < System.currentTimeMillis()) throw new OperationTimedOut("Javascript read timed out");
		}
		System.out.println("Finished waiting");

		webClient.close();

		Document document = Jsoup.parse(page.asXml());
		Element tagElement = document.getElementsByClass("clanTag").get(0);
		Element avatarElement = document.getElementsByClass("avatarContainer").last().getAllElements().last();
		String avatar = data(avatarElement.outerHtml(), "src");

		int count = Integer.parseInt(data(document.getElementsByClass("count").get(0).outerHtml(), "data-count"));

		Clan clan = new Clan(clanName, tagElement.html(), avatar, count);

		Element element = document.getElementById("clanMembersList");
		Elements groups = element.children();
		groups.remove(0); // Removing preload placeholder

		int rank = 0;
		for (Element currentGroup : groups) {
			Element container = currentGroup.getAllElements().get(0);
			Elements groupMembers = container.getElementsByClass("media");
			for (Element currentMember : groupMembers) {
				Element memberInfo = currentMember.children().last();
				String playerName = memberInfo.children().get(0).text().trim();
				clan.addMember(ClanRank.byOrdinal(rank), playerName);
			}
			rank++;
		}

		return clan;

	}

	private static String data(String html, String search) {

		String begin = search + "=\"";

		html = html.substring(html.indexOf(begin) + begin.length());
		html = html.substring(0, html.indexOf("\""));

		return html;

	}

	private static int iterableSize(Iterable<?> iterable) {
		int size = 0;
		for (Object ignored : iterable) {
			size++;
		}
		return size;
	}

	public static final ErrorHandler CSS_ERROR_HANDLER = new ErrorHandler() {

		@Override
		public void warning(CSSParseException e) throws CSSException { }

		@Override
		public void error(CSSParseException e) throws CSSException { }

		@Override
		public void fatalError(CSSParseException e) throws CSSException { }

	};

	public static final JavaScriptErrorListener JAVA_SCRIPT_ERROR_LISTENER = new JavaScriptErrorListener() {

		@Override
		public void scriptException(InteractivePage interactivePage, ScriptException e) { }

		@Override
		public void timeoutError(InteractivePage interactivePage, long l, long l1) {

		}

		@Override
		public void malformedScriptURL(InteractivePage interactivePage, String s, MalformedURLException e) { }

		@Override
		public void loadScriptError(InteractivePage interactivePage, URL url, Exception e) { }

	};

}
