# -*- coding:utf-8 -*-
import requests
from requests import Request, Session
import bs4
from bs4 import BeautifulSoup
import os
import udatetime

def send_request(url, method="get", req_header=None, timeout=3000, params: dict=None, **kwargs) -> BeautifulSoup:
	"""[summary]
	Send HTTP Request
	Args:
		url ([type]): [description]
		method (str, optional): [description]. Defaults to "get".
		req_header ([type], optional): [description]. Defaults to None.
		timeout (int, optional): [description]. Defaults to 3000.
		params (dict, optional): [description]. Defaults to None.

	Returns:
		BeautifulSoup: [description]
	"""
 	session = requests.Session()
	bs_document = None
	req_obj = requests.Request(method, url, params, kwargs)
	prepped = req_obj.prepare()
	response = session.send(prepped, timeout=timeout)
	if response is not None:
		status_code = response.status_code
		encoding = response.encoding
		content_type = response.headers['content-type']
		print(response.content)
	return bs_document

def download_image(url, save_directory, file_name):
	pass

#################### Util START #####################################
def log_in_console(msg, color) -> None:
	print("[" + udatetime.now_time_as_string() + "] " + msg)


#################### Util END #######################################



