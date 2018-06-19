package base;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import beans.ChatBeans;
import dao.ChatDao;

@ServerEndpoint("/broadcast")
public class WebSocket {

	ArrayList<Session> sessionList = new ArrayList<Session>();

	@OnOpen
	public void onOpen(Session session) {

		sessionList.add(session);

	}

	@OnMessage
	public void broadcast(String message) throws IOException {

		String[] messages = message.split(",");

		if(messages[0].equals("投稿")) {

			//アイテムIDを数値に変換
			int numberItemId = 0;
			//数値への変換
			try {
				numberItemId = Integer.parseInt(messages[1]);
			} catch (NumberFormatException nfex) {
			}

			//データベース操作
			ChatDao chatDao = new ChatDao();

			//コメントを登録
			chatDao.CommentTouroku(numberItemId, messages[2], messages[3]);

			//チャットリストを取得
			ArrayList<ChatBeans> chatList = chatDao.CommentList(numberItemId);

			//送信メッセージを設定
			String sendMessage = "{";
			for(int i = 0; i < chatList.size(); i++) {

				//日付をフォーマット
				chatList.get(i).setFormatDate(chatList.get(i).getCreateDate());

				if(i == chatList.size() - 1) {

					String title = " \"chat" + i + "\" : ";
					String name = " {\"name\": \" " + chatList.get(i).getName() +  " \", ";
					String comment = " \"comment\": \" " + chatList.get(i).getComment() +  " \", ";
					String date = " \"date\": \" " + chatList.get(i).getFormatDate() +  " \"} ";

					sendMessage += title + name + comment + date;

				}else {

					String title = " \"chat" + i + "\" : ";
					String name = " {\"name\": \" " + chatList.get(i).getName() +  " \", ";
					String comment = " \"comment\": \" " + chatList.get(i).getComment() +  " \", ";
					String date = " \"date\": \" " + chatList.get(i).getFormatDate() +  " \"}, ";

					sendMessage += title + name + comment + date;

				}

			}
			sendMessage += "}";

			for(Session session : sessionList) {
				session.getBasicRemote().sendText(sendMessage);
			}

		}else if(messages[0].equals("更新")) {

			//アイテムIDを数値に変換
			int numberItemId = 0;
			//数値への変換
			try {
				numberItemId = Integer.parseInt(messages[1]);
			} catch (NumberFormatException nfex) {
			}

			//データベース操作
			ChatDao chatDao = new ChatDao();

			//チャットリストを取得
			ArrayList<ChatBeans> chatList = chatDao.CommentList(numberItemId);

			//送信メッセージを設定
			String sendMessage = "{";
			for(int i = 0; i < chatList.size(); i++) {

				//日付をフォーマット
				chatList.get(i).setFormatDate(chatList.get(i).getCreateDate());

				if(i == chatList.size() - 1) {

					String title = " \"chat" + i + "\" : ";
					String name = " {\"name\": \" " + chatList.get(i).getName() +  " \", ";
					String comment = " \"comment\": \" " + chatList.get(i).getComment() +  " \", ";
					String date = " \"date\": \" " + chatList.get(i).getFormatDate() +  " \"} ";

					sendMessage += title + name + comment + date;

				}else {

					String title = " \"chat" + i + "\" : ";
					String name = " {\"name\": \" " + chatList.get(i).getName() +  " \", ";
					String comment = " \"comment\": \" " + chatList.get(i).getComment() +  " \", ";
					String date = " \"date\": \" " + chatList.get(i).getFormatDate() +  " \"}, ";

					sendMessage += title + name + comment + date;

				}

			}
			sendMessage += "}";

			for(Session session : sessionList) {
				session.getBasicRemote().sendText(sendMessage);
			}

		}

	}

	@OnClose
	public void onClose(Session session) {

		sessionList.remove(session);

	}

}
