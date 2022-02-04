import datetime

def now_time_as_string() -> str:
	return datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')