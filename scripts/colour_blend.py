import timeit
from typing import Tuple, Any, Callable
import re

def timed_function(func: Callable[..., Any]) -> Callable[..., Any]:
    """Annotation to time a function to compare performance"""
    def wrapper(*args: Any, **kwargs: Any) -> Any:
        start_time = timeit.default_timer()
        result = func(*args, **kwargs)
        end_time = timeit.default_timer()
        print("Function {} took {:.6f} seconds to execute.".format(func.__name__, end_time - start_time))
        return result
    return wrapper

def hex_to_rgb(hex_colour: str) -> Tuple[int, int, int]:
    """Converts a hex colour code to an RGB tuple."""
    hex_colour = hex_colour.lstrip("#")
    return tuple(int(hex_colour[i:i+2], 16) for i in (0, 2, 4))

def rgb_to_hex(rgb_colour: Tuple[int, int, int]) -> str:
    """Converts an RGB tuple to a hex colour code."""
    return "#{:02x}{:02x}{:02x}".format(*rgb_colour)

@timed_function
def interpolate_hex_colours(start_hex: str, end_hex: str, steps: int) -> None:
    """Interpolates between two hex colours with a set number of steps and displays the resulting colours."""
    start_rgb = hex_to_rgb(start_hex)
    end_rgb = hex_to_rgb(end_hex)
    r_step = (end_rgb[0] - start_rgb[0]) / (steps - 1)
    g_step = (end_rgb[1] - start_rgb[1]) / (steps - 1)
    b_step = (end_rgb[2] - start_rgb[2]) / (steps - 1)
    for i in range(steps):
        r = int(start_rgb[0] + i * r_step)
        g = int(start_rgb[1] + i * g_step)
        b = int(start_rgb[2] + i * b_step)
        colour_hex = rgb_to_hex((r, g, b))
        print("\033[48;2;{};{};{}m  \033[0m".format(r, g, b), end="")
    print("\n")

@timed_function
def blend_colours(hex_colour1: str, hex_colour2: str, weight: float) -> str:
    """
    Blend two hex colours and display the result as a rendered colour.
    """
    if not re.match(r'^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$', hex_colour1):
        raise ValueError("Invalid hex colour: {}".format(hex_colour1))
    if not re.match(r'^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$', hex_colour2):
        raise ValueError("Invalid hex colour: {}".format(hex_colour2))
    if not 0 <= weight <= 1:
        raise ValueError("Invalid weight: {}".format(weight))

    rgb1 = tuple(int(hex_colour1[i:i+2], 16) for i in (1, 3, 5))
    rgb2 = tuple(int(hex_colour2[i:i+2], 16) for i in (1, 3, 5))

    blended_rgb = tuple(int(round(w*c1 + (1-w)*c2)) for c1, c2 in zip(rgb1, rgb2) for w in [weight])

    blended_hex = "#{:02x}{:02x}{:02x}".format(*blended_rgb)

    print("\033[48;2;{};{};{}m    \033[0m".format(*rgb1), end='')
    print("\033[48;2;{};{};{}m    \033[0m".format(*blended_rgb), end='')
    print("\033[48;2;{};{};{}m    \033[0m".format(*rgb2))
    print("\n")

    return blended_hex

if __name__ == '__main__':
    interpolate_hex_colours("#12baba", "#Fb00aF", 10)
    blend_colours("#12baba", "#Fb00aF", 0.5)
