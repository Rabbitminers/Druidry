import timeit
from typing import Tuple, Any, Callable

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

if __name__ == '__main__':
    interpolate_hex_colours("#12baba", "#Fb00aF", 10)
