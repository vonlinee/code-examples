# import regex


DEFAULT_REPLACE = {
	'[': "",
	"]": "",
	"{": "",
	"}": "",
	"\n": ""
}


def multi_replace(src: str, mappings : dict) -> str:
	for key in mappings.keys():
		src = src.replace(key, mappings.get(key))
	return src


class StringTokenizer:

	src: str

	def __init__(self, delimiter):
		src = ""

