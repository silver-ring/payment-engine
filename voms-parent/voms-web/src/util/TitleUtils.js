export function getPageTitle(props) {
  return (props.location.pathname).substring(1).split(/(?=[A-Z])/).join(" ");
}
