package com.sftp.service.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SftpConfig {

	@Value("${sftp.host}")
	private String host;

	@Value("${sftp.port}")
	private Integer port;

	@Value("${sftp.username}")
	private String username;

	@Value("${sftp.password}")
	private String password;

	@Value("${sftp.sessionTimeout}")
	private Integer sessionTimeout;

	@Value("${sftp.channelTimeout}")
	private Integer channelTimeout;

	public boolean uploadFile(String localFilePath, String remoteFilePath) {

		ChannelSftp channelSftp = createChannelSftp();
		try {
			channelSftp.put(localFilePath, remoteFilePath);
			return true;

		} catch (SftpException ex) {
			log.error("Error upload file", ex);
		} finally {
			disconnectChannelSftp(channelSftp);
		}
		return false;
	}

	public boolean downloadFile(String localFilePath, String remoteFilePath) {

		ChannelSftp channelSftp = createChannelSftp();
		OutputStream outputStream;

		try {
			File file = new File(localFilePath);
			outputStream = new FileOutputStream(file);
			channelSftp.get(remoteFilePath, outputStream);
			file.createNewFile();
			return true;

		} catch (SftpException | IOException ex) {
			log.error("Error download file", ex);
		} finally {
			disconnectChannelSftp(channelSftp);
		}
		return false;
	}

	public boolean fileExistControl(String filePath, String filename) {

		ChannelSftp channelSftp = createChannelSftp();
		try {
			channelSftp.cd(filePath);
			String path = channelSftp.ls(filename).toString();
			if (!path.contains(filename)) {
				return false;
			} else
				return true;
		} catch (Exception e) {
			log.error("Error file exist", e);
		} finally {
			disconnectChannelSftp(channelSftp);
		}

		return false;
	}

	public ChannelSftp createChannelSftp() {

		try {
			JSch jSch = new JSch();
			Session session = jSch.getSession(username, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect(sessionTimeout);
			Channel channel = session.openChannel("sftp");
			channel.connect(channelTimeout);
			return (ChannelSftp) channel;

		} catch (JSchException ex) {
			log.error("Create ChannelSftp error", ex);
		}
		return null;
	}

	public void disconnectChannelSftp(ChannelSftp channelSftp) {

		try {
			if (channelSftp == null)
				return;
			if (channelSftp.isConnected())
				channelSftp.disconnect();
			if (channelSftp.getSession() != null)
				channelSftp.getSession().disconnect();

		} catch (Exception ex) {
			log.error("SFTP disconnect error", ex);
		}

	}

}