from enum import Enum


class Language(Enum):
    """Enumeration for different programming languages."""

    UNKNOWN_LANGUAGE = 0
    PYTHON = 1
    CPP = 2
    PYTHON3 = 3
    JAVA = 4

    def __str__(self) -> str:
        """Return the lowercase name of the language."""
        return self.name.lower()

    @staticmethod
    def idx_to_lang(idx: int) -> str:
        """Convert a language index to its lowercase name.

        Args:
            idx (int): The index of the language in the enumeration.

        Returns:
            str: The name of the language corresponding to the index, in lowercase.
        """
        try:
            return Language(idx).name.lower()
        except ValueError:
            return Language.UNKNOWN_LANGUAGE.name.lower()

    def to_suffix(self) -> str:
        """Convert a language to its file suffix.

        Returns:
            str: The file suffix of the language.
        """
        if self == Language.PYTHON:
            return "py"
        elif self == Language.CPP:
            return "cpp"
        elif self == Language.PYTHON3:
            return "py"
        elif self == Language.JAVA:
            return "java"
        else:
            return "unknown"
