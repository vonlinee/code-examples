import datetime


def now_time_as_string(format: str='%Y-%m-%d %H:%M:%S') -> str:
	return datetime.datetime.now().strftime(format)
